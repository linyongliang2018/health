import org.junit.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test01 {
    @Test
    public void mima() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("admin"));
        boolean admin = encoder.matches("admin", "$2a$10$khPECbkhK1dHgZ7R8cR8r.NMZLuCbpSwielqVFkU3FrXtASre4wvC");
        System.out.println(admin);
    }
}
