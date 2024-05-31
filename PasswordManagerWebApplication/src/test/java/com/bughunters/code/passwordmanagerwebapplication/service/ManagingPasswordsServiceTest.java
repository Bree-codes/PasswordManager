package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.PasswordManaged;
import com.bughunters.code.passwordmanagerwebapplication.models.UpdatingPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import com.bughunters.code.passwordmanagerwebapplication.repository.UpdatedPasswordsRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagingPasswordsServiceTest {

    @Mock
    private CryptoDetailsUtils cryptoDetailsUtils;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ManagedPasswordsRepository passwordsRepository;

    @Mock
    private UpdatedPasswordsRepositories updatedPasswordsRepository;

    @InjectMocks
    private ManagingPasswordsService managingPasswordsService;

    private ManagedPassword managedPassword;
    private UpdatingPasswordsDetails managingPasswords;
    private UpdatedPasswordsDetails updatedPassword;

    @BeforeEach
    void setUp() {
        managedPassword = new ManagedPassword();
        managedPassword.setManagedPasswordId(1L);
        managedPassword.setUserId(1L);
        managedPassword.setWebsiteName("example.com");
        managedPassword.setUsername("user");
        managedPassword.setPassword("encryptedPassword");

        managingPasswords = new UpdatingPasswordsDetails("12", "user","example.com");

        updatedPassword = new UpdatedPasswordsDetails();
        updatedPassword.setUserid(1L);
        updatedPassword.setManagedPasswordId(managedPassword.getManagedPasswordId());
        updatedPassword.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void testManagePasswords() throws Exception {
        List<UpdatingPasswordsDetails> passwordsList = Collections.singletonList(managingPasswords);
        List<ManagedPassword> managedPasswordList = Collections.singletonList(managedPassword);
        List<MappedDetailsResponse> responseList = new ArrayList<>();
        MappedDetailsResponse response = new MappedDetailsResponse();
        responseList.add(response);

        when(modelMapper.map(any(ManagedPassword.class), eq(MappedDetailsResponse.class))).thenReturn(response);
        when(cryptoDetailsUtils.encrypt(managingPasswords.getPassword())).thenReturn("encryptedPassword");

        //List<MappedDetailsResponse> result = managingPasswordsService.managePasswords(passwordsList);

        //assertEquals(responseList.size(), result.size());
        verify(passwordsRepository, times(1)).saveAll(managedPasswordList);
    }

    @Test
    void testDecrypt() throws Exception {
        List<ManagedPassword> managedPasswords = Collections.singletonList(managedPassword);
       // List<ManagingPasswords> decryptedPasswords = Collections.singletonList(managingPasswords);

        when(passwordsRepository.findAllByUserId(1L)).thenReturn(Optional.of(managedPasswords));
        when(cryptoDetailsUtils.decrypt("encryptedPassword")).thenReturn("password");

        List<PasswordManaged> result = managingPasswordsService.decrypt(1L);

        //assertEquals(decryptedPasswords.size(), result.size());
        //assertEquals(decryptedPasswords.get(0).getPassword(), result.get(0).getPassword());
    }

    @Test
    void testUpdateDetails() throws Exception {
        when(passwordsRepository.findByUserIdAndManagedPasswordId(1L,1L)).thenReturn(Optional.of(managedPassword));
        when(cryptoDetailsUtils.encrypt(managingPasswords.getPassword())).thenReturn("encryptedPassword");

        ManagingPasswords updated = managingPasswordsService.updateDetails(1L, managingPasswords, Long.parseLong(""));

        assertEquals(managingPasswords.getWebsiteName(), updated.getWebsiteName());
        assertEquals(managingPasswords.getUsername(), updated.getUsername());
        verify(updatedPasswordsRepository, times(1)).save(any(UpdatedPasswordsDetails.class));
        verify(passwordsRepository, times(1)).save(any(ManagedPassword.class));
    }

    @Test
    void testDeletePasswordByUserIdAndManaged() {
        when(passwordsRepository.findByUserIdAndManagedPasswordId(1L, managedPassword.getManagedPasswordId()))
                .thenReturn(Optional.of(managedPassword));

        ResponseEntity<String> response = managingPasswordsService.deletePasswordByUserIdAndManaged(1L, managedPassword.getManagedPasswordId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(passwordsRepository, times(1)).delete(managedPassword);
    }
}
