package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.repository.PasswordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagingPasswordServiceTest {

    @Mock
    private CryptoDetailsUtils cryptoDetailsUtils;

    @Mock
    private PasswordsRepository passwordsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ManagingPasswordsService managingPasswordService;

    @Test
    void testManagingPasswords() throws Exception {
        // Given
        List<ManagingPasswords> passwords = Arrays.asList(
                new ManagingPasswords( 1, "password1", "website1"),
                new ManagingPasswords(2, "password2", "website2")
        );

        // When
        List<ManagingPasswords> result = managingPasswordService.managingPasswords(passwords);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cryptoDetailsUtils, times(2)).initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
        verify(cryptoDetailsUtils, times(2)).encrypt(anyString());
        verify(passwordsRepository).saveAll(anyList());
    }

    @Test
    void testManagingPasswords_WhenExceptionOccurs() throws Exception {
        // Given
        List<ManagingPasswords> passwords = Arrays.asList(
                new ManagingPasswords( 1, "password1", "website1"),
                new ManagingPasswords(2, "password2", "website2")
        );

        // When
        when(cryptoDetailsUtils.encrypt(anyString())).thenThrow(new RuntimeException("Error encrypting password"));

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> managingPasswordService.managingPasswords(passwords));
        assertEquals("Error encrypting password", exception.getMessage());
    }
}
