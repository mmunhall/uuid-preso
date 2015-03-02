//@GrabConfig(systemClassLoader=true)
//@Grab(group='com.oracle', module='ojdbc6', version='11.2.0.2.0')


@Grapes([
    @Grab(group='com.oracle', module='ojdbc6', version='11.2.0.2.0'),
    @GrabConfig(systemClassLoader=true)
])
import oracle.jdbc.driver.OracleDriver
import groovy.sql.Sql

class dbUtil {

    public static getSql() {
        Sql.newInstance(
            'jdbc:oracle:thin:@oraclehost:1521:devvm',
            'uuid',
            'uuid',
            'oracle.jdbc.driver.OracleDriver'
        )
    }
}
