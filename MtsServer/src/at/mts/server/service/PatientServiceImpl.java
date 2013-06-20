package at.mts.server.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import at.mts.entity.Bodyparts;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;
import at.mts.entity.cda.CdaIdV;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PersistenceException;

public class PatientServiceImpl implements PatientService {
	public PatientServiceImpl(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	
	private PatientDao patientDao;

	@Override
	public List<Patient> findAll() throws ServiceException {
		try {
			return patientDao.findAll();
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}

	@Override
	public void clear() throws ServiceException {
		try {
			patientDao.clear();
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}
	
	@Override
	public Patient findById(UUID id) throws ServiceException {
		try {
			return patientDao.findById(id);
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}

	@Override
	public Patient findByIdV(UUID id, int version) throws ServiceException {
		try {
			return patientDao.findByIdV(id, version);
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}

	@Override
	public List<Patient> findBy(TriageCategory category, Treatment treatment)
			throws ServiceException {
		try {
			return patientDao.findBy(category, treatment);
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}
	
	private Patient mergePatient(Patient base, Patient update1, Patient update2) {
		// Result Patient
		Patient r = new Patient();
		
		r.setId(base.getId());
		//r.setVersion(-1);
		r.setTimestamp(new Date());
		r.setCategory(merge(base.getCategory(), update1.getCategory(), update2.getCategory()));
		r.setSalvageInfo(merge(base.getSalvageInfoString(), update1.getSalvageInfoString(), update2.getSalvageInfoString()));
		r.setNameGiven(merge(base.getNameGiven(), update1.getNameGiven(), update2.getNameGiven()));
		r.setNameFamily(merge(base.getNameFamily(), update1.getNameFamily(), update2.getNameFamily()));
		r.setBirthTime(merge(base.getBirthTime(), update1.getBirthTime(), update2.getBirthTime()));
		r.setGender(merge(base.getGender(), update1.getGender(), update2.getGender()));
		r.setWalkable(merge(base.getWalkable(), update1.getWalkable(), update2.getWalkable()));
		r.setRespiration(merge(base.getRespiration(), update1.getRespiration(), update2.getRespiration()));
		r.setPerfusion(merge(base.getPerfusion(), update1.getPerfusion(), update2.getPerfusion()));
		r.setMentalStatus(merge(base.getMentalStatus(), update1.getMentalStatus(), update2.getMentalStatus()));
		r.setPhaseOfLife(merge(base.getPhaseOfLife(), update1.getPhaseOfLife(), update2.getPhaseOfLife()));
		r.setPlacePosition(merge(base.getPlacePosition(), update1.getPlacePosition(), update2.getPlacePosition()));
		r.setUrgency(merge(base.getUrgency(), update1.getUrgency(), update2.getUrgency()));
		r.setBloodPressureSystolic(merge(base.getBloodPressureSystolic(), update1.getBloodPressureSystolic(), update2.getBloodPressureSystolic()));
		r.setBloodPressureDiastolic(merge(base.getBloodPressureDiastolic(), update1.getBloodPressureDiastolic(), update2.getBloodPressureDiastolic()));
		r.setPulse(merge(base.getPulse(), update1.getPulse(), update2.getPulse()));
		r.setReadyForTransport(merge(base.getReadyForTransport(), update1.getReadyForTransport(), update2.getReadyForTransport()));
		r.setHospital(merge(base.getHospital(), update1.getHospital(), update2.getHospital()));
		r.setHealthInsurance(merge(base.getHealthInsurance(), update1.getHealthInsurance(), update2.getHealthInsurance()));
		r.setTreatment(merge(base.getTreatment(), update1.getTreatment(), update2.getTreatment()));
		
		r.setDiagnosis(stringMerge(base.getDiagnosisString(), update1.getDiagnosisString(), update2.getDiagnosisString()));
		r.setCourseOfTreatment(stringMerge(base.getCourseOfTreatmentString(), update1.getCourseOfTreatmentString(), update2.getCourseOfTreatmentString()));
		
		for (String bodyPartKey : Bodyparts.Keys) {
			String m1 = base.getBodyparts().get(bodyPartKey);
			String m2 = update1.getBodyparts().get(bodyPartKey);
			String m3 = update2.getBodyparts().get(bodyPartKey);
			
			String m = stringMerge(m1, m2, m3);
			
			if (m != null) {
				r.getBodyparts().set(bodyPartKey, m);
			}
		}
		
		return r;
	}
	
	private <T> T merge(T base, T update1, T update2) {
		if (!objectEqual(base, update2)) {
			return update2;
		}
		else {
			return update1;
		}
	}
	
	private String stringMerge(String base, String update1, String update2) {
		
		if (base == null && update1 == null && update2 == null) {
			return null;
		}
		else {
			if (base == null) { base = ""; }
			if (update1 == null) { update1 = ""; }
			if (update2 == null) { update2 = ""; }
			
			String message = update2;
			
			if (update1.contains(message)) { message = update1; }
			else if (!message.contains(update1)) { message = update1 + "; " + message; }
			
			if (base.contains(message)) { message = base; }
			else if (!message.contains(base)) { message = base + "; " + message; }
			
			return message;
		}
	}
	
	private boolean objectEqual(Object a, Object b) {
		return (a == b) ||
				((a != null) && (a.equals(b)));
	}
	
	@Override
	public void update(CdaDocument cda, Date timestamp) throws ServiceException {
		try {
			
			Patient patientDb = patientDao.findById(cda.getPatientId());
			Patient patientUpdate = new Patient(cda);
			
			if (patientDb == null) {
				
				patientDao.update(patientUpdate);
				
			}
			else {
			
				Patient base;
				
				CdaIdV parentIdV = cda.getParentIdV();
				if (parentIdV != null) {
					base = patientDao.findByIdV(parentIdV.getId(), parentIdV.getVersion());
				}
				else {
					base = patientDao.findById(cda.getPatientId());
				}
				
				if (base == null) { // wenn es kein Parent gibt: leer Patient als Parent
					base = new Patient();
				}
				
				Patient update1;
				Patient update2;
				
				if (patientUpdate.getTimestamp().before(patientDb.getTimestamp())) {
					update1 = patientUpdate;
					update2 = patientDb;
				}
				else {
					update1 = patientDb;
					update2 = patientUpdate;
				}
				
				Patient mergedUpdate = mergePatient(base, update1, update2);
				
				mergedUpdate.setAuthorId(patientUpdate.getAuthorId());
				mergedUpdate.setAuthorNameFamily(patientUpdate.getAuthorNameFamily());
				mergedUpdate.setAuthorNameGiven(patientUpdate.getAuthorNameGiven());
				
				patientDao.update(mergedUpdate);
			}

		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}
}
