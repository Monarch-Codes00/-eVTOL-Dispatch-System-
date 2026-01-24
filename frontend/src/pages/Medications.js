import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Plus, Pill, Hash, Weight, Trash2 } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const Medications = () => {
  const [meds, setMeds] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newMed, setNewMed] = useState({
    name: '',
    weight: 10,
    code: '',
    image: 'https://images.unsplash.com/photo-1584017911766-d451b3d0e843?w=500&auto=format&fit=crop&q=60'
  });

  const fetchMeds = async () => {
    try {
      const res = await axios.get('/api/medications');
      setMeds(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchMeds();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/medications', newMed);
      setShowModal(false);
      fetchMeds();
      setNewMed({ name: '', weight: 10, code: '', image: 'https://images.unsplash.com/photo-1584017911766-d451b3d0e843?w=500&auto=format&fit=crop&q=60' });
    } catch (err) {
      alert(err.response?.data?.details?.name || err.response?.data?.details?.code || err.response?.data?.message || "Error creating medication");
    }
  };

  const handleDelete = async (id) => {
    if(!window.confirm("Are you sure?")) return;
    try {
      await axios.delete(`/api/medications/${id}`);
      fetchMeds();
    } catch (err) {
      alert(err.response?.data?.message || "Error deleting medication");
    }
  };

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <div>
          <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>Medication Inventory</h2>
          <p style={{ color: '#64748b' }}>Manage medicine cases and specifications</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          <Plus size={20} /> Add Medication
        </button>
      </div>

      <div className="grid">
        {meds.map((med) => (
          <MedCard key={med.id} med={med} onDelete={() => handleDelete(med.id)} />
        ))}
      </div>

      <AnimatePresence>
        {showModal && (
          <div style={{
            position: 'fixed', top: 0, left: 0, width: '100%', height: '100%',
            background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', 
            alignItems: 'center', zIndex: 1000, backdropFilter: 'blur(4px)'
          }}>
            <motion.div 
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.9, opacity: 0 }}
              className="glass card" 
              style={{ width: '100%', maxWidth: '500px', background: 'white' }}
            >
              <h3 style={{ marginBottom: '1.5rem' }}>Add Medication</h3>
              <form onSubmit={handleSubmit}>
                <div className="input-group">
                  <label>Name (Alpha-numeric, '-', '_')</label>
                  <input 
                    required pattern="^[a-zA-Z0-9_-]+$"
                    placeholder="e.g. Paracetamol_500"
                    value={newMed.name}
                    onChange={(e) => setNewMed({...newMed, name: e.target.value})}
                  />
                </div>
                <div className="input-group">
                  <label>Weight (grams)</label>
                  <input 
                    type="number" min="1"
                    value={newMed.weight}
                    onChange={(e) => setNewMed({...newMed, weight: parseInt(e.target.value)})}
                  />
                </div>
                <div className="input-group">
                  <label>Code (Uppercase, Numbers, '_')</label>
                  <input 
                    required pattern="^[A-Z0-9_]+$"
                    placeholder="e.g. MED_CODE_001"
                    value={newMed.code}
                    onChange={(e) => setNewMed({...newMed, code: e.target.value})}
                  />
                </div>
                <div className="input-group">
                  <label>Image URL</label>
                  <input 
                    value={newMed.image}
                    onChange={(e) => setNewMed({...newMed, image: e.target.value})}
                  />
                </div>
                <div style={{ display: 'flex', gap: '1rem', marginTop: '2rem' }}>
                  <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>Add Item</button>
                  <button type="button" className="btn" style={{ flex: 1, background: '#f1f5f9' }} onClick={() => setShowModal(false)}>Cancel</button>
                </div>
              </form>
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </div>
  );
};

const MedCard = ({ med, onDelete }) => (
  <motion.div layout className="glass card" style={{ overflow: 'hidden', padding: 0 }}>
    <img 
      src={med.image} 
      alt={med.name} 
      style={{ width: '100%', height: '160px', objectFit: 'cover' }}
      onError={(e) => e.target.src = 'https://images.unsplash.com/photo-1584017911766-d451b3d0e843?w=500'}
    />
    <div style={{ padding: '1.5rem' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>{med.name}</h4>
        <button onClick={onDelete} style={{ background: 'none', border: 'none', color: '#94a3b8', cursor: 'pointer' }}>
          <Trash2 size={18} />
        </button>
      </div>
      <div style={{ marginTop: '1rem', display: 'flex', flexDirection: 'column', gap: '0.4rem' }}>
        <p style={{ fontSize: '0.85rem', color: '#64748b', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Hash size={14} /> {med.code}
        </p>
        <p style={{ fontSize: '0.85rem', color: '#64748b', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <Weight size={14} /> {med.weight}g
        </p>
      </div>
    </div>
  </motion.div>
);

export default Medications;
