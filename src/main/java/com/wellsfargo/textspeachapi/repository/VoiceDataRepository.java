package com.wellsfargo.textspeachapi.repository;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceDataRepository extends JpaRepository<VoiceData, Integer> {
    public VoiceData findByUid(String uid);
    public VoiceData findByEmail(String email);
    public VoiceData findByEmployeeId(Integer employeeId);

}
