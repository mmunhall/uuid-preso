import groovy.sql.Sql
import java.util.UUID
this.class.classLoader.rootLoader.addURL( new URL("file:////Users/mikemunhall/.m2/repository/com/oracle/ojdbc6/11.2.0.2.0/ojdbc6-11.2.0.2.0.jar") )

// cleanup from previous execution
sql.execute("delete from people")

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// create UUIDs:
def uuidVersion4 = UUID.randomUUID()
def uuidVersion3 = UUID.nameUUIDFromBytes("some string".getBytes())
println "Version 4 UUID: ${uuidVersion4.toString()}"
println "Version 3 UUID: ${uuidVersion3.toString()}"

// INSERT:
sql.execute("insert into people (first_name, last_name) values ('Bob', 'Griese')")

// SELECT:
def rows = sql.rows("select id, rawtohex(id) idAsHex, first_name, last_name from people")
println "New row: ${rows[0]}"
println "Bob's ID: ${rows[0].id}"
println "Bob's hex ID: ${rows[0].idAsHex}"

// create a person object:
// FAIL (id is raw): def person = new Person(id: rows[0].id, firstName: rows[0].first_name, lastName: rows[0].last_name)
// FAIL (id is hex string): def person = new Person(id: rows[0].idAsHex, firstName: rows[0].first_name, lastName: rows[0].last_name)
def person = new Person(id: hexToUuid(rows[0].idAsHex), firstName: rows[0].first_name, lastName: rows[0].last_name)
println "Person object: ${person}"

// UPDATE:
person.firstName = "Bobbie"
person.lastName = "Tables"

// FAIL: sql.executeUpdate("update people set first_name = ? where id = ?", [person.firstName, person.id])
// FAIL: sql.executeUpdate("update people set first_name = ? where id = hextoraw(?)", [person.firstName, person.id])
sql.executeUpdate("update people set first_name = ? where id = hextoraw(?)", [person.firstName, uuidToHex(person.id)])





// Given an unformatted hexidecimal string of 16 digits, returns a UUID.
def hexToUuid(hex) {
    String formattedHex = hex.replaceAll(/(\w{8})(\w{4})(\w{4})(\w{4})(\w{12})/, "\$1-\$2-\$3-\$4-\$5");
    UUID.fromString(formattedHex);
}

// Converts a UUID to a pure hexidecimal value
def uuidToHex(uuid) {
    def hex = uuid.toString();
    hex = hex.toLowerCase();
    hex = hex.replaceAll("-", "");
    hex;
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

def getSql() {
    Sql.newInstance(
        'jdbc:oracle:thin:@oraclehost:1521:devvm',
        'uuid',
        'uuid',
        'oracle.jdbc.driver.OracleDriver'
    )
}

class Person {
    UUID id
    String firstName
    String lastName

    public String toString() {
        "$firstName $lastName (${id.toString()})"
    }
}
