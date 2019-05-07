package com.DAOFactory;

import com.Bean.users;
import com.DAOlmpl.DAOlmpl;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class DAOFactoryTest {
    @Test
    void getConnection() {
        DAOlmpl<users> u= new DAOlmpl<>(users.class);
        users p=new users();
        p.setActive_code("123");
        p.setEmail("123");
        p.setNickname("123");
        p.setPassword("123");
        p.setPrivilege(1);
        p.setSex(true);
        p.setState(1);
        p.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        p.setUsername("123");
        //u.add(p);

        p.setId(2);
        p.setPassword("321");
        u.alter(2,p);

        users u1=u.Query(1,2);
        System.out.println(u1.getPassword());

        //u.delete(1,1);
    }
}