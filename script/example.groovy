import groovy.sql.Sql
this.class.classLoader.rootLoader.addURL( new URL("file:////Users/mikemunhall/.m2/repository/com/oracle/ojdbc6/11.2.0.2.0/ojdbc6-11.2.0.2.0.jar") )



def rows = sql.rows("select id, rawtohex(id) idAsHex, first_name, last_name from people")

println "id (raw): ${rows[0].id}"
println "id (hex): ${rows[0].idAsHex}"






/*def operators = sql.rows("select * from operator order by id").collect { it.id }
def feeds = sql.rows("select * from feed order by id").collect { it.id }

def combined = GroovyCollections.combinations([operators, feeds])
combined.each {
    println """<OPERATOR_FEED OPERATOR_ID="${it[0]}" FEED_ID="${it[1]}" GOAL_ALLOCATION="1" CIP_ENABLED="1"/>"""
}*/








def private getSql() {
    Sql.newInstance(
        'jdbc:oracle:thin:@oraclehost:1521:devvm',
        'uuid',
        'uuid',
        'oracle.jdbc.driver.OracleDriver'
    )
}
