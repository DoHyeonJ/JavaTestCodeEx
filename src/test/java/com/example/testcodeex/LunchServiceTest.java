package com.example.testcodeex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LunchServiceTest {

    @Test
    void createLunchService() { // mock() 메소드로 만드는 방법
        LunchRepository lunchRepository = mock(LunchRepository.class);
        LunchService lunchService = new LunchService(lunchRepository);
        assertNotNull(lunchService);
    }

    @Mock
    LunchRepository lunchRepository;

    @Test
    void createLunchService2() { // 애노테이션을 활용하여 만드는 방법
        LunchService lunchService = new LunchService(lunchRepository);
        assertNotNull(lunchService);
    }

    @Test
    void createLunchService3(@Mock LunchRepository lunchRepository) { // 파라미터로 받아서 사용하는 방법
        LunchService lunchService = new LunchService(lunchRepository);
        assertNotNull(lunchService);
    }

}