package com.wellsfargo.textspeachapi.repository;

import com.wellsfargo.textspeachapi.model.VoiceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceDataRepository extends JpaRepository<VoiceData, Long> {
}
