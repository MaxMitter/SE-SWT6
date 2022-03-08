package swt6.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LogBookEntry.class)
public abstract class LogBookEntry_ {

	public static volatile SingularAttribute<LogBookEntry, String> activity;
	public static volatile SingularAttribute<LogBookEntry, LocalDateTime> startTime;
	public static volatile SingularAttribute<LogBookEntry, Long> id;
	public static volatile SingularAttribute<LogBookEntry, LocalDateTime> endTime;
	public static volatile SingularAttribute<LogBookEntry, Employee> employee;

	public static final String ACTIVITY = "activity";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String EMPLOYEE = "employee";

}

