import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Truck, Package, CheckCircle2, AlertCircle } from 'lucide-react';
import { motion } from 'framer-motion';

const LoadMedication = () => {
  const [availableDrones, setAvailableDrones] = useState([]);
  const [availableMeds, setAvailableMeds] = useState([]);
  const [selectedDrone, setSelectedDrone] = useState('');
  const [selectedMeds, setSelectedMeds] = useState([]);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState(null);

  const fetchData = async () => {
    try {
      const [dronesRes, medsRes] = await Promise.all([
        axios.get('/api/evtol/available'),
        axios.get('/api/medications/available')
      ]);
      setAvailableDrones(dronesRes.data);
      setAvailableMeds(medsRes.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const toggleMed = (code) => {
    if (selectedMeds.includes(code)) {
      setSelectedMeds(selectedMeds.filter(c => c !== code));
    } else {
      setSelectedMeds([...selectedMeds, code]);
    }
  };

  const handleLoad = async () => {
    if (!selectedDrone || selectedMeds.length === 0) return;
    
    setLoading(true);
    setStatus(null);
    try {
      await axios.post('/api/evtol/load', {
        evtolSerialNumber: selectedDrone,
        medicationCodes: selectedMeds
      });
      setStatus({ type: 'success', message: `Successfully loaded ${selectedMeds.length} items onto ${selectedDrone}` });
      setSelectedMeds([]);
      setSelectedDrone('');
      fetchData();
    } catch (err) {
      setStatus({ type: 'error', message: err.response?.data?.message || "Internal Dispatch Error" });
    } finally {
      setLoading(false);
    }
  };

  const currentDrone = availableDrones.find(d => d.serialNumber === selectedDrone);
  const selectedMedsData = availableMeds.filter(m => selectedMeds.includes(m.code));
  const totalWeight = selectedMedsData.reduce((acc, m) => acc + m.weight, 0);
  const isOverweight = currentDrone && (currentDrone.currentLoad + totalWeight > currentDrone.weightLimit);

  return (
    <div className="container">
      <div style={{ marginBottom: '2rem' }}>
        <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>Dispatch Controller</h2>
        <p style={{ color: '#64748b' }}>Load medications onto available eVTOL units</p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '2rem' }}>
        <div className="glass card">
          <h3 style={{ marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
            <Truck size={20} color="#2563eb" /> 1. Select Drone
          </h3>
          <div className="input-group">
            <select 
              value={selectedDrone} 
              onChange={(e) => setSelectedDrone(e.target.value)}
              style={{ fontSize: '1rem' }}
            >
              <option value="">Select an available drone...</option>
              {availableDrones.map(d => (
                <option key={d.id} value={d.serialNumber}>
                  {d.serialNumber} ({d.model} - Cap: {d.weightLimit - d.currentLoad}g free)
                </option>
              ))}
            </select>
          </div>
          
          {currentDrone && (
            <motion.div 
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              style={{ background: '#f8fafc', padding: '1rem', borderRadius: '8px', fontSize: '0.9rem' }}
            >
              <p><strong>Drone:</strong> {currentDrone.serialNumber}</p>
              <p><strong>Model:</strong> {currentDrone.model}</p>
              <p><strong>Battery:</strong> {currentDrone.batteryCapacity}%</p>
              <p><strong>Weight Limit:</strong> {currentDrone.weightLimit}g</p>
              <p><strong>Current Load:</strong> {currentDrone.currentLoad}g</p>
            </motion.div>
          )}

          <h3 style={{ margin: '2rem 0 1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
            <Package size={20} color="#2563eb" /> 2. Select Items
          </h3>
          <div style={{ maxHeight: '300px', overflowY: 'auto', border: '1px solid #e2e8f0', borderRadius: '8px' }}>
            {availableMeds.length === 0 ? (
              <p style={{ padding: '1rem', color: '#64748b', textAlign: 'center' }}>No medications available at base.</p>
            ) : (
              availableMeds.map(med => (
                <div 
                  key={med.id} 
                  onClick={() => toggleMed(med.code)}
                  style={{
                    padding: '0.75rem 1rem',
                    borderBottom: '1px solid #f1f5f9',
                    cursor: 'pointer',
                    background: selectedMeds.includes(med.code) ? '#eff6ff' : 'transparent',
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    transition: 'all 0.2s'
                  }}
                >
                  <span>{med.name} ({med.weight}g)</span>
                  {selectedMeds.includes(med.code) && <CheckCircle2 size={18} color="#2563eb" />}
                </div>
              ))
            )}
          </div>
        </div>

        <div className="glass card" style={{ display: 'flex', flexDirection: 'column' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Manifest Summary</h3>
          
          <div style={{ flex: 1 }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
              <span>Items selected:</span>
              <span style={{ fontWeight: 700 }}>{selectedMeds.length}</span>
            </div>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
              <span>Manifest Weight:</span>
              <span style={{ fontWeight: 700, color: isOverweight ? '#ef4444' : 'inherit' }}>{totalWeight}g</span>
            </div>
            {currentDrone && (
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
                <span>Remaining Capacity:</span>
                <span style={{ fontWeight: 700 }}>{currentDrone.weightLimit - currentDrone.currentLoad - totalWeight}g</span>
              </div>
            )}
            
            <hr style={{ margin: '1.5rem 0', border: 'none', borderTop: '1px solid #e2e8f0' }} />
            
            {status && (
              <div style={{ 
                padding: '1rem', 
                borderRadius: '8px', 
                background: status.type === 'success' ? '#dcfce7' : '#fee2e2',
                color: status.type === 'success' ? '#166534' : '#991b1b',
                marginBottom: '1.5rem',
                display: 'flex',
                gap: '0.5rem',
                alignItems: 'center'
              }}>
                {status.type === 'success' ? <CheckCircle2 size={18} /> : <AlertCircle size={18} />}
                {status.message}
              </div>
            )}

            {isOverweight && (
              <div style={{ padding: '0.75rem', color: '#ef4444', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                <AlertCircle size={14} /> Attention: Payload exceeds available capacity.
              </div>
            )}
          </div>

          <button 
            className="btn btn-primary" 
            style={{ width: '100%', padding: '1rem', marginTop: 'auto' }}
            disabled={!selectedDrone || selectedMeds.length === 0 || isOverweight || loading}
            onClick={handleLoad}
          >
            {loading ? 'Processing Dispatch...' : 'Authorize Dispatch & Load'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoadMedication;
