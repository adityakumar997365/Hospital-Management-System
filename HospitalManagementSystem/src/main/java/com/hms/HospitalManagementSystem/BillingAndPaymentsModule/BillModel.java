package com.hms.HospitalManagementSystem.BillingAndPaymentsModule;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hms.HospitalManagementSystem.DoctorModule.DoctorModel;
import com.hms.HospitalManagementSystem.FamilyMemberModule.FamilyMemberModel;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bill")
public class BillModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId")
    private Integer billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private PatientModel patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private DoctorModel doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyMemberId", nullable = true) // Nullable because it is null if the primary patient books for themselves
    private FamilyMemberModel familyMember;

    @Column(name = "totalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus")
    private PaymentStatus paymentStatus;
    
 // Remove the database column definition entirely
    @Column(name = "billDate", nullable = false)
    private LocalDate billDate;

    @PrePersist
    protected void onCreate() {
        this.billDate = LocalDate.now(); // Sets the date in Java automatically
    }

    public enum PaymentStatus {
        PAID, UNPAID
    }

    public BillModel() {}

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
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

	public DoctorModel getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorModel doctor) {
        this.doctor = doctor;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }
}
