import java.util.UUID;

UUID uuid = UUID.randomUUID();

println(uuid.toString());

UUID uuid2 = UUID.fromString(uuid.toString().toUpperCase().replace("-", ""));

println(uuid2.toString());
