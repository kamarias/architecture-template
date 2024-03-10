package io.github.kamarias.ap;

import io.github.kamarias.api.DemoServiceImpl;
import io.github.kamarias.application.UserExecute;
import io.github.kamarias.vo.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;



/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/27 21:43
 */
@ExtendWith(MockitoExtension.class)
public class DemoServiceTest {


    @Mock
    private UserExecute userExecute;


    @InjectMocks
    private DemoServiceImpl demoService;

    @Test
    public void mockList() {
        List mockedList  = Mockito.mock(List.class);
        mockedList.add("one");
        mockedList.clear();
        Mockito.verify(mockedList).add("one");
        Mockito.verify(mockedList).clear();
    }


    @Test
    public void testSayHello(){
//        Mockito.doReturn("123").when(userExecute.saveUser(Mockito.any()));
        Mockito.when(userExecute.saveUser(Mockito.any())).thenReturn("asdfghjkl");
        System.out.println(demoService.sayHello(new Request()));
        Mockito.doReturn("123").when(userExecute).saveUser(Mockito.any());
        System.out.println(demoService.sayHello(new Request()));
    }



}
