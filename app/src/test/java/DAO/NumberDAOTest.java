package DAO;

import entities.Number;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberDAOTest {

    NumberDAO numberDAO = new NumberDAO();

    @Test
    void deleteTest() {
        Number entity = numberDAO.getEntityById(89375555269L, Number.class);
        numberDAO.delete(entity);
        assertNull(numberDAO.getEntityById(89375555269L, Number.class));
    }
}