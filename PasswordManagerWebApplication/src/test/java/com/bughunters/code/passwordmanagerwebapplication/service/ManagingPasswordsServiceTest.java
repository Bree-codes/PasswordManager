package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagingPasswordsServiceTest {

    @Mock
    private CryptoDetailsUtils cryptoDetailsUtils;

    @Mock
    private ManagedPasswordsRepository passwordsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ManagingPasswordsService managingPasswordsService;

    private ManagedPassword managedPassword;
    private ManagingPasswords managingPasswords;

    @BeforeEach
    public void setUp() {
        managedPassword = new ManagedPassword();
        managedPassword.setUserId(1L);
        managedPassword.setWebsiteName("example.com");
        managedPassword.setPassword("encryptedPassword");

        managingPasswords = new ManagingPasswords(1L, "decryptedPassword", "example.com");
    }

    @Test
    public void testManagePasswords() throws Exception {
        List<ManagingPasswords> passwordsList = List.of(managingPasswords);
        when(cryptoDetailsUtils.encrypt(anyString())).thenReturn("encryptedPassword");

        List<ManagingPasswords> result = managingPasswordsService.managePasswords(passwordsList);

        verify(passwordsRepository, times(1)).saveAll(anyList());
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getPassword()).isEqualTo("encryptedPassword");
    }

    @Test
    public void testDecrypt() throws Exception {
        when(passwordsRepository.findAllByUserId(1L)).thenReturn(Optional.of(List.of(managedPassword)));
        when(cryptoDetailsUtils.decrypt(anyString())).thenReturn("decryptedPassword");

        List<ManagingPasswords> result = managingPasswordsService.decrypt(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getPassword()).isEqualTo("decryptedPassword");
    }

    @Test
    public void testDecrypt_NotFound() {
        when(passwordsRepository.findAllByUserId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managingPasswordsService.decrypt(1L))
                .isInstanceOf(ChangeSetPersister.NotFoundException.class);
    }

    @Test
    public void testUpdateDetails() throws Exception {
        when(passwordsRepository.findByUserId(1L)).thenReturn(Optional.of(managedPassword));
        when(cryptoDetailsUtils.encrypt(anyString())).thenReturn("encryptedPassword");

        ManagingPasswords result = managingPasswordsService.updateDetails(1L, managingPasswords);

        ArgumentCaptor<ManagedPassword> passwordCaptor = ArgumentCaptor.forClass(ManagedPassword.class);
        verify(passwordsRepository).save(passwordCaptor.capture());
        ManagedPassword capturedPassword = passwordCaptor.getValue();

        assertThat(capturedPassword.getPassword()).isEqualTo("encryptedPassword");
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo("encryptedPassword");
    }

    @Test
    public void testUpdateDetails_NotFound() {
        when(passwordsRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managingPasswordsService.updateDetails(1L, managingPasswords))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password with userId 1 not found");
    }
}
