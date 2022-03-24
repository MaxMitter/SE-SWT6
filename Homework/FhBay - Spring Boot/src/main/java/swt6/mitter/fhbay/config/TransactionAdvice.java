package swt6.mitter.fhbay.config;

import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import swt6.mitter.fhbay.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Aspect
@Component
public class TransactionAdvice {

    protected Logger logger = LoggerFactory.getLogger(TransactionAdvice.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Around(value = "execution(public * swt6.mitter.fhbay.shell.*.*(..))")
    public Object transactionAround(ProceedingJoinPoint pjp) throws Throwable {
        if (entityManagerFactory == null)
            throw new IllegalArgumentException("Property 'entityManagerFactory' is required");

        boolean participate = false;
        if (TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
            participate = true;
        } else {
            JpaUtil.openEntityManager(entityManagerFactory);
        }

        Object ret = null;

        try {
            ret = pjp.proceed(); // delegates to method of target class.
        } finally {
            if (!participate)
                JpaUtil.closeEntityManager(entityManagerFactory);
        }
        return ret;
    }
}
