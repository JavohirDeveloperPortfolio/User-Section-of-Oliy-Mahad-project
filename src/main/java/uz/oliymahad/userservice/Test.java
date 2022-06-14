package uz.oliymahad.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {


    public static void main(String[] args) {

        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }
}
