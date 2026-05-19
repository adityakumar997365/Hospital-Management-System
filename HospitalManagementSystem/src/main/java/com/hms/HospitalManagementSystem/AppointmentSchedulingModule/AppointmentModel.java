package com.hms.HospitalManagementSystem.AppointmentSchedulingModule;

import java.time.LocalDate;

import com.hms.HospitalManagementSystem.BillingAndPaymentsModule.BillModel;
import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.PatientModule.PatientModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointment")
public class AppointmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId")
    private Integer appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private PatientModel patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private DoctorModel doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private BillModel bill;

    public BillModel getBill() {
		return bill;
	}

	public void setBill(BillModel bill) {
		this.bill = bill;
	}

	@Column(name = "appointmentDate")
    private LocalDate appointmentDate;

    @Column(name = "timeSlot", length = 20)
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        CONFIRMED, CANCELLED, COMPLETED, REVIEWING
    }

    public AppointmentModel() {}

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public DoctorModel getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorModel doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
