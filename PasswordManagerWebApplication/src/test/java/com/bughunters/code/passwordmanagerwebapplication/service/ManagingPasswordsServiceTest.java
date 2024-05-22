package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagingPasswordsServiceTest {

    @Mock
    private CryptoDetailsUtils cryptoDetailsUtils;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ManagedPasswordsRepository passwordsRepository;

    @InjectMocks
    private ManagingPasswordsService managingPasswordsService;

    private ManagingPasswords managingPasswords;
    private ManagedPassword managedPassword;

    @BeforeEach
    void setUp() {
        managingPasswords = new ManagingPasswords(1L, "decryptedPassword", "username", "websiteName");
        managedPassword = new ManagedPassword();
        managedPassword.setUserId(1L);
        managedPassword.setWebsiteName("websiteName");
        managedPassword.setUsername("username");
        managedPassword.setPassword("encryptedPassword");
    }

    @Test
    void testManagePasswords() {
        when(modelMapper.map(any(ManagingPasswords.class), eq(ManagedPassword.class))).thenReturn(managedPassword);
        when(passwordsRepository.saveAll(anyList())).thenReturn(Collections.singletonList(managedPassword));
        when(modelMapper.map(any(ManagedPassword.class), eq(MappedDetailsResponse.class))).thenReturn(new MappedDetailsResponse());

        List<MappedDetailsResponse> result = managingPasswordsService.managePasswords(Collections.singletonList(managingPasswords));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(passwordsRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testDecrypt() throws Exception {
        when(passwordsRepository.findAllByUserId(anyLong())).thenReturn(Optional.of(Collections.singletonList(managedPassword)));
        when(cryptoDetailsUtils.decrypt(anyString())).thenReturn("decryptedPassword");

        List<ManagingPasswords> result = managingPasswordsService.decrypt(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("decryptedPassword", result.get(0).getPassword());
        verify(passwordsRepository, times(1)).findAllByUserId(anyLong());
    }

    @Test
    void testUpdateDetails() throws Exception {
        when(passwordsRepository.findByUserId(anyLong())).thenReturn(Optional.of(managedPassword));
        when(passwordsRepository.save(any(ManagedPassword.class))).thenReturn(managedPassword);
        when(cryptoDetailsUtils.encrypt(anyString())).thenReturn("encryptedPassword");
        when(modelMapper.map(any(ManagedPassword.class), eq(ManagingPasswords.class))).thenReturn(managingPasswords);

        ManagingPasswords result = managingPasswordsService.updateDetails(1L, managingPasswords);

        assertNotNull(result);
        assertEquals("websiteName", result.getWebsiteName());
        verify(passwordsRepository, times(1)).findByUserId(anyLong());
        verify(passwordsRepository, times(1)).save(any(ManagedPassword.class));
    }

    @Test
    void testDeletePasswordByUserIdAndPasswordId() {
        when(passwordsRepository.findByUserIdAndManagedPasswordId(anyLong(), anyString())).thenReturn(Optional.of(managedPassword));

        ResponseEntity<String> response = managingPasswordsService. deletePasswordByUserIdAndManaged(1L, "1L");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted successfully", response.getBody());
        verify(passwordsRepository, times(1)).findByUserIdAndManagedPasswordId(anyLong(), anyString());
        verify(passwordsRepository, times(1)).delete(any(ManagedPassword.class));
    }
}
