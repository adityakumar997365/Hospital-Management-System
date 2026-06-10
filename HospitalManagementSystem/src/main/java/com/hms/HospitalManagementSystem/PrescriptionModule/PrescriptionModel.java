package com.hms.HospitalManagementSystem.PrescriptionModule;

import java.time.LocalDate;

import com.hms.HospitalManagementSystem.AppointmentSchedulingModule.AppointmentModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Prescription")
public class PrescriptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescriptionId")
    private Integer prescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private DoctorModel doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private PatientModel patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyMemberId", nullable = true) // Nullable because it is null if treating the primary patient
    private FamilyMemberModel familyMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentId")
    private AppointmentModel appointment;

    @Column(name = "diagnose", length = 255)
    private String diagnose;

    @Column(name = "medicine", length = 255)
    private String medicine;

    @Column(name = "dosage", length = 255)
    private String dosage;

    @Column(name = "createdDate")
    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    public PrescriptionModel() {}

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public DoctorModel getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorModel doctor) {
        this.doctor = doctor;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public FamilyMemberModel getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(FamilyMemberModel familyMember) {
		this.familyMember = familyMember;
	}

	public AppointmentModel getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentModel appointment) {
        this.appointment = appointment;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
