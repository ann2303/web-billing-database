package DAO;

import entities.Number;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberDAOTest {

    NumberDAOImpl numberDAOImpl = new NumberDAOImpl();

    @Test
    void deleteTest() {
        Number entity = numberDAOImpl.getEntityById(89375555269L, Number.class);
        numberDAOImpl.delete(entity);
        assertNull(numberDAOImpl.getEntityById(89375555269L, Number.class));
    }
}