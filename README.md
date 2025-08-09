# 🚀 AminyoMCLib

[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0.0-green.svg)](https://github.com/aminyo/AminyoMCLib)
[![Spigot](https://img.shields.io/badge/Spigot-1.7.10--1.21.8-yellow.svg)](https://www.spigotmc.org/)
[![Paper](https://img.shields.io/badge/Paper-1.8+-red.svg)](https://papermc.io/)
[![BungeeCord](https://img.shields.io/badge/BungeeCord-Latest-purple.svg)](https://www.spigotmc.org/wiki/bungeecord/)
[![Velocity](https://img.shields.io/badge/Velocity-3.0+-cyan.svg)](https://velocitypowered.com/)

# 🚀 AminyoMCLib

[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0.0-green.svg)](https://github.com/aminyo/AminyoMCLib)
[![Spigot](https://img.shields.io/badge/Spigot-1.7.10--1.21.8-yellow.svg)](https://www.spigotmc.org/)
[![Paper](https://img.shields.io/badge/Paper-1.8+-red.svg)](https://papermc.io/)
[![BungeeCord](https://img.shields.io/badge/BungeeCord-Latest-purple.svg)](https://www.spigotmc.org/wiki/bungeecord/)
[![Velocity](https://img.shields.io/badge/Velocity-3.0+-cyan.svg)](https://velocitypowered.com/)

> **Una libreria Minecraft moderna e completa per sviluppatori Java**

AminyoMCLib è una libreria multipiattaforma progettata per semplificare lo sviluppo di plugin Minecraft, supportando Spigot, Paper, BungeeCord, Velocity e altre piattaforme popolari.

---

## 📋 Indice

- [✨ Caratteristiche](#-caratteristiche)
- [🎯 Piattaforme Supportate](#-piattaforme-supportate)
- [⚡ Installazione](#-installazione)
- [🚀 Guida Rapida](#-guida-rapida)
- [📚 Documentazione](#-documentazione)
- [🛠️ Moduli](#️-moduli)
- [💾 Database](#-database)
- [📁 File Manager](#-file-manager)
- [🎨 Utilities](#-utilities)
- [📖 Esempi](#-esempi)
- [🤝 Contribuire](#-contribuire)
- [📜 Licenza](#-licenza)

---

## ✨ Caratteristiche

### 🎯 **Multipiattaforma**
- ✅ **Spigot/Bukkit** (1.7.10 - 1.21.8)
- ✅ **Paper** (1.8+)
- ✅ **BungeeCord** (Tutte le versioni)
- ✅ **Velocity** (3.0+)
- ✅ **Waterfall** (Compatibile con BungeeCord)

### 🚀 **Funzionalità Avanzate**
- 🔥 **API Unificata** - Un'unica API per tutte le piattaforme
- 🗄️ **Database Manager** - MySQL, PostgreSQL, MongoDB, SQLite, H2, JSON
- 📁 **File Manager** - YAML, JSON, TOML, Properties con auto-reload
- 🎨 **Text Utils** - Colori hex, formattazione, progress bar
- ⚡ **Async/Sync** - Operazioni asincrone e sincrone
- 🧰 **Utilities** - Math, Time, Location, Player, Reflection
- 🎯 **Version Support** - Compatibilità tra versioni diverse

### 💡 **Developer Friendly**
- 📖 **Documentazione Completa**
- 🧪 **Esempi Pratici**
- 🔧 **Setup Semplice**
- 🚀 **Performance Ottimizzate**

---

## 🎯 Piattaforme Supportate

| Piattaforma | Versioni | Stato | Note |
|-------------|----------|-------|------|
| ![Spigot](https://img.shields.io/badge/Spigot-orange) | 1.7.10 - 1.21.8 | ✅ Completo | Tutte le funzionalità |
| ![Paper](https://img.shields.io/badge/Paper-red) | 1.8+ | ✅ Completo | Ottimizzazioni extra |
| ![Bukkit](https://img.shields.io/badge/Bukkit-blue) | 1.7.10+ | ✅ Completo | Compatibilità legacy |
| ![BungeeCord](https://img.shields.io/badge/BungeeCord-purple) | Tutte | ✅ Completo | Proxy features |
| ![Velocity](https://img.shields.io/badge/Velocity-cyan) | 3.0+ | 🚧 In sviluppo | Prossima versione |
| ![Waterfall](https://img.shields.io/badge/Waterfall-teal) | Tutte | ✅ Compatibile | Via BungeeCord API |

---

## ⚡ Installazione

### 📥 Maven

```xml
<dependency>
    <groupId>it.aminyo</groupId>
    <artifactId>aminyo-mclib</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 📥 Gradle

```groovy
implementation 'it.aminyo:aminyo-mclib:1.0.0'
```

### 📦 JAR Download

Scarica l'ultima versione da [GitHub Releases](https://github.com/aminyo/AminyoMCLib/releases)

---

## 🚀 Guida Rapida

### 🔧 Setup Base

#### **Per Spigot/Paper**

```java
public class MioPlugin extends JavaPlugin {
    private AminyoMCLib aminyoLib;
    
    @Override
    public void onEnable() {
        // Inizializza AminyoMCLib
        SpigotPlatformAdapter adapter = new SpigotPlatformAdapter(this);
        this.aminyoLib = AminyoMCLibImpl.initialize(adapter);
        
        getLogger().info("Plugin abilitato con AminyoMCLib!");
        
        // Esempio: Comando semplice
        registerCommand(new MioComando(this));
    }
    
    @Override
    public void onDisable() {
        if (aminyoLib != null) {
            aminyoLib.shutdown();
        }
    }
}
```

#### **Per BungeeCord**

```java
public class MioPlugin extends Plugin {
    private AminyoMCLib aminyoLib;
    
    @Override
    public void onEnable() {
        // Inizializza AminyoMCLib
        BungeePlatformAdapter adapter = new BungeePlatformAdapter(this);
        this.aminyoLib = AminyoMCLibImpl.initialize(adapter);
        
        getLogger().info("Plugin abilitato con AminyoMCLib!");
    }
}
```

### 💬 Primo Comando

```java
public class MioComando extends SpigotCommand {
    
    public MioComando(JavaPlugin plugin) {
        super(plugin, "test", "mioplugin.test", "Comando di test");
    }
    
    @Override
    protected void execute(CommandSender sender, String[] args) {
        // Messaggio colorato
        sendMessage(sender, "&aHello &b{player}&a!", 
            Map.of("player", sender.getName()));
        
        // Info piattaforma
        AminyoMCLib lib = AminyoMCLibImpl.getInstance();
        sendMessage(sender, "&7Platform: &f" + lib.getPlatformAdapter().getPlatformType());
        sendMessage(sender, "&7Version: &f" + lib.getPlatformAdapter().getMinecraftVersion());
    }
}
```

---

## 📚 Documentazione

### 🏗️ Architettura

AminyoMCLib è costruita con un'architettura modulare che separa le funzionalità specifiche della piattaforma dall'API comune:

```
AminyoMCLib
├── 🏛️ Core API (Interfaces comuni)
├── 🎯 Platform Adapters (Spigot, BungeeCord, etc.)
├── 🗄️ Database Module (MySQL, MongoDB, JSON, etc.)
├── 📁 File Module (YAML, JSON, TOML, Properties)
└── 🧰 Utils Module (Text, Math, Time, Player, etc.)
```

### 🔌 Platform Adapter

Il `PlatformAdapter` fornisce un'interfaccia unificata per tutte le piattaforme:

```java
public interface PlatformAdapter {
    PlatformType getPlatformType();
    String getMinecraftVersion();
    CompletableFuture<Void> runAsync(Runnable task);
    CompletableFuture<Void> runSync(Runnable task);
    String getDataFolder();
    boolean supportsColors();
}
```

---

## 🛠️ Moduli

### 📊 Panoramica Moduli

| Modulo | Descrizione | Stato |
|--------|-------------|-------|
| **Core** | API base e gestione piattaforma | ✅ |
| **Database** | Gestione database multipli | ✅ |
| **File** | Manager per file di configurazione | ✅ |
| **Utils** | Utilities comuni per sviluppo | ✅ |
| **Command** | Framework per comandi avanzato | ✅ |
| **Event** | Sistema eventi cross-platform | 🚧 |
| **GUI** | Menu e interfacce grafiche | 🚧 |

---

## 💾 Database

### 🗄️ Database Supportati

AminyoMCLib supporta una vasta gamma di database:

| Database | Tipo | Driver | Esempio |
|----------|------|--------|---------|
| **MySQL** | SQL | `mysql-connector-j` | Server produzione |
| **MariaDB** | SQL | `mariadb-java-client` | Alternative a MySQL |
| **PostgreSQL** | SQL | `postgresql` | Database enterprise |
| **SQLite** | SQL | `sqlite-jdbc` | Database locale |
| **H2** | SQL | `h2` | Testing e sviluppo |
| **MongoDB** | NoSQL | `mongodb-driver` | Documenti JSON |
| **JSON** | File | Built-in | Configurazioni semplici |

### ⚙️ Configurazione Database

```java
// Configurazione MySQL
DatabaseConfig config = new DatabaseConfig(DatabaseType.MYSQL);
config.setHost("localhost");
config.setPort(3306);
config.setDatabase("minecraft");
config.setUsername("user");
config.setPassword("password");
config.setMaxPoolSize(20);

// Creazione manager
DatabaseModule dbModule = new DatabaseModule(getDataFolder());
DatabaseManager dbManager = dbModule.createDatabaseManager("main", config);
```

### 💡 Esempi Database

#### **Query SQL Sincrone**

```java
// Esecuzione query
QueryResult result = dbManager.executeQuery(
    "SELECT * FROM players WHERE uuid = ?", 
    playerUuid
);

// Lettura risultati
for (Map<String, Object> row : result.getRows()) {
    String name = (String) row.get("name");
    int level = (Integer) row.get("level");
    // Processa dati...
}
```

#### **Operazioni Asincrone**

```java
// Query asincrona
dbManager.executeQueryAsync("SELECT * FROM stats WHERE player_id = ?", playerId)
    .thenAccept(result -> {
        // Processa risultati in thread separato
        updatePlayerStats(result);
    })
    .thenRunAsync(() -> {
        // Torna al thread principale per operazioni Bukkit
        player.sendMessage("§aStats updated!");
    }, bukkitScheduler::runTask);
```

#### **MongoDB Operations**

```java
MongoDatabaseManager mongoManager = (MongoDatabaseManager) dbManager;

// Inserimento documento
Document player = new Document("uuid", playerUuid)
    .append("name", playerName)
    .append("level", 10)
    .append("lastLogin", new Date());

mongoManager.insertDocument("players", player)
    .thenRun(() -> logger.info("Player saved to MongoDB"));

// Ricerca documenti
Document filter = new Document("level", new Document("$gte", 50));
mongoManager.findDocuments("players", filter)
    .thenAccept(documents -> {
        logger.info("Found {} high-level players", documents.size());
    });
```

---

## 📁 File Manager

### 📝 Formati Supportati

- **YAML** (.yml, .yaml) - Configurazioni readable
- **JSON** (.json) - Dati strutturati
- **TOML** (.toml) - Tom's Obvious, Minimal Language  
- **Properties** (.properties) - Formato Java standard

### 🔧 Utilizzo File Manager

```java
FileManager fileManager = new FileManager(getDataFolder());

// Carica file YAML
YamlFileModel config = fileManager.getYamlFile("config.yml");

// Lettura valori
String serverName = config.getString("server.name", "Default Server");
int maxPlayers = config.getInt("server.maxPlayers", 100);
List<String> admins = config.getStringList("server.admins");

// Scrittura valori
config.set("server.lastRestart", System.currentTimeMillis());
config.set("stats.totalLogins", config.getInt("stats.totalLogins", 0) + 1);

// Auto-save abilitato
config.setAutoSave(true); // Salva automaticamente ad ogni modifica
```

### 🎛️ Configurazione Avanzata

```java
// File JSON per dati complessi
JsonFileModel playerData = fileManager.getJsonFile("players.json");

// Struttura dati nidificata
Map<String, Object> playerInfo = Map.of(
    "uuid", player.getUniqueId().toString(),
    "stats", Map.of(
        "kills", 10,
        "deaths", 3,
        "playtime", 3600000L
    ),
    "locations", Map.of(
        "home", Map.of(
            "world", "world",
            "x", 100.5,
            "y", 64.0,
            "z", 200.3
        )
    )
);

playerData.set("players." + player.getName(), playerInfo);
```

---

## 🎨 Utilities

### 🌈 Text Utils

AminyoMCLib offre potenti utilities per la gestione del testo:

```java
// Colori hex (1.16+) e legacy
String message = TextUtils.colorize("&aHello &#ff5555World&r!");

// Progress bar personalizzate
String progressBar = TextUtils.createProgressBar(75, 100, 20, '█', '░');
// Output: "███████████████░░░░░"

// Centratura testo
String centeredTitle = TextUtils.center("=== WELCOME ===", 50, ' ');

// Wrap testo
List<String> wrappedLines = TextUtils.wrapText(longText, 40);

// Placeholder sostituzione
String welcome = TextUtils.replacePlaceholders(
    "Welcome {player} to {server}!",
    Map.of(
        "player", player.getName(),
        "server", "MyServer"
    )
);
```

### ⏰ Time Utils

```java
// Parsing tempo human-friendly
long duration = TimeUtils.parseTime("1h 30m 45s"); // milliseconds

// Formattazione durata
String formatted = TimeUtils.formatTime(duration); // "1h 30m 45s"

// Timestamps
String timestamp = TimeUtils.getCurrentTimestamp(); // "2024-01-15 14:30:25"
String custom = TimeUtils.getCurrentTimestamp("dd/MM/yyyy HH:mm");

// Controllo scadenza
boolean expired = TimeUtils.hasElapsed(lastLoginTime, Duration.ofDays(30).toMillis());
```

### 🧮 Math Utils

```java
// Clamping valori
int health = MathUtils.clamp(playerHealth, 0, 20);
double damage = MathUtils.clamp(baseDamage * multiplier, 0.0, 999.9);

// Numeri random
int randomLevel = MathUtils.randomInt(1, 100);
double randomMultiplier = MathUtils.randomDouble(0.8, 1.2);

// Percentuali
double percentage = MathUtils.percentage(currentXP, maxXP); // 0.0 - 1.0

// Formattazione numeri
String formatted = MathUtils.formatNumber(123456.789, 2); // "123456.79"
```

### 📍 Location Utils

```java
// Location cross-platform
SimpleLocation loc = new SimpleLocation("world", 100.5, 64, 200.3, 180f, 0f);

// Calcolo distanze
double distance = LocationUtils.distance(loc1, loc2);
double distance2D = LocationUtils.distance2D(x1, z1, x2, z2);

// Parsing da stringa
SimpleLocation parsed = LocationUtils.parseLocation("world:100,64,200:180,0");

// Area checking
boolean inArea = LocationUtils.isWithinArea(playerLoc, corner1, corner2);
```

### 👤 Player Utils

```java
// Validazione nomi
boolean validName = PlayerUtils.isValidPlayerName("Aminyo"); // true
boolean validUUID = PlayerUtils.isValidUUID(uuid.toString());

// UUID formatting
String formatted = PlayerUtils.formatUUID(trimmedUuid);
String trimmed = PlayerUtils.trimUUID(formattedUuid);

// Per Spigot - utilities specifiche
Player player = SpigotPlayerUtils.getPlayer("Aminyo");
SpigotPlayerUtils.sendMessage(player, "&aWelcome back!");
SpigotPlayerUtils.broadcast("&6Server restarting in 5 minutes!");
```

---

## 📖 Esempi

### 🏆 Plugin Completo di Esempio

```java
public class ExamplePlugin extends SpigotAminyoPlugin {
    
    private FileManager fileManager;
    private DatabaseModule databaseModule;
    
    @Override
    public void onEnable() {
        super.onEnable(); // Inizializza AminyoMCLib
        
        // Setup file manager
        this.fileManager = new FileManager(getDataFolder().getAbsolutePath());
        
        // Carica configurazione
        YamlFileModel config = fileManager.getYamlFile("config.yml");
        setupDefaultConfig(config);
        
        // Setup database se abilitato
        if (config.getBoolean("database.enabled", false)) {
            setupDatabase(config);
        }
        
        // Registra comandi e listener
        registerCommands();
        registerListeners();
        
        getLogger().info("ExamplePlugin abilitato!");
    }
    
    private void setupDefaultConfig(YamlFileModel config) {
        // Valori di default
        Map<String, Object> defaults = Map.of(
            "plugin.name", "ExamplePlugin",
            "plugin.prefix", "&8[&6Example&8] ",
            "database.enabled", false,
            "database.type", "SQLITE",
            "database.file", "data.db",
            "features.welcomeMessage", true,
            "features.autoSave", true,
            "messages.welcome", "&aWelcome {player} to the server!",
            "messages.goodbye", "&cGoodbye {player}!"
        );
        
        defaults.forEach((key, value) -> {
            if (!config.contains(key)) {
                config.set(key, value);
            }
        });
        
        config.save();
    }
    
    private void setupDatabase(YamlFileModel config) {
        this.databaseModule = new DatabaseModule(getDataFolder().getAbsolutePath());
        databaseModule.enable();
        
        // Configura database
        DatabaseConfig dbConfig = new DatabaseConfig(
            DatabaseType.valueOf(config.getString("database.type", "SQLITE"))
        );
        
        if (dbConfig.getType().isFile()) {
            dbConfig.setFilePath(config.getString("database.file", "data.db"));
        } else {
            dbConfig.setHost(config.getString("database.host", "localhost"));
            dbConfig.setPort(config.getInt("database.port", 3306));
            dbConfig.setDatabase(config.getString("database.name", "minecraft"));
            dbConfig.setUsername(config.getString("database.username", "root"));
            dbConfig.setPassword(config.getString("database.password", ""));
        }
        
        try {
            DatabaseManager dbManager = databaseModule.createDatabaseManager("main", dbConfig);
            
            // Crea tabelle se necessario
            createTables(dbManager);
            
            getLogger().info("Database connesso: " + dbConfig.getType());
        } catch (Exception e) {
            getLogger().severe("Errore connessione database: " + e.getMessage());
        }
    }
    
    private void createTables(DatabaseManager dbManager) throws Exception {
        // Tabella giocatori
        dbManager.executeUpdate("""
            CREATE TABLE IF NOT EXISTS players (
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                uuid VARCHAR(36) UNIQUE NOT NULL,
                name VARCHAR(16) NOT NULL,
                first_join BIGINT NOT NULL,
                last_join BIGINT NOT NULL,
                total_playtime BIGINT DEFAULT 0,
                level INTEGER DEFAULT 1,
                experience BIGINT DEFAULT 0
            )
        """);
        
        // Indici per performance
        dbManager.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_uuid ON players(uuid)");
        dbManager.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_name ON players(name)");
    }
    
    private void registerCommands() {
        registerCommand(new InfoCommand(this));
        registerCommand(new StatsCommand(this));
        registerCommand(new ReloadCommand(this));
    }
    
    private void registerListeners() {
        registerListener(new PlayerJoinListener(this));
        registerListener(new PlayerQuitListener(this));
    }
}
```

### 🎯 Comando Avanzato

```java
public class StatsCommand extends SpigotCommand {
    
    private final ExamplePlugin plugin;
    private final DatabaseManager dbManager;
    
    public StatsCommand(ExamplePlugin plugin) {
        super(plugin, "stats", "example.stats", "View player statistics");
        this.plugin = plugin;
        this.dbManager = plugin.getDatabaseModule().getDatabaseManager("main");
    }
    
    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!isPlayer(sender)) {
            sendMessage(sender, "&cThis command can only be used by players!");
            return;
        }
        
        Player player = getPlayer(sender);
        String targetPlayer = args.length > 0 ? args[0] : player.getName();
        
        // Query asincrona per non bloccare il server
        dbManager.executeQueryAsync(
            "SELECT * FROM players WHERE name = ? OR uuid = ?",
            targetPlayer, targetPlayer
        ).thenAccept(result -> {
            
            if (result.isEmpty()) {
                sendMessage(sender, "&cPlayer not found: " + targetPlayer);
                return;
            }
            
            Map<String, Object> data = result.getFirstRow();
            
            // Torna al thread principale per inviare il messaggio
            plugin.getScheduler().runSync(() -> {
                sendStatsMessage(sender, data);
            });
            
        }).exceptionally(throwable -> {
            plugin.getLogger().severe("Error querying player stats: " + throwable.getMessage());
            sendMessage(sender, "&cError retrieving stats!");
            return null;
        });
    }
    
    private void sendStatsMessage(CommandSender sender, Map<String, Object> data) {
        String name = (String) data.get("name");
        long firstJoin = (Long) data.get("first_join");
        long lastJoin = (Long) data.get("last_join");
        long playtime = (Long) data.get("total_playtime");
        int level = (Integer) data.get("level");
        long experience = (Long) data.get("experience");
        
        // Header decorativo
        sendMessage(sender, "");
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        sendMessage(sender, TextUtils.center("&6&lPLAYER STATS", 30, ' '));
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        
        // Statistiche
        sendMessage(sender, "");
        sendMessage(sender, "&7Player: &f" + name);
        sendMessage(sender, "&7Level: &a" + level + " &8(&7" + experience + " XP&8)");
        sendMessage(sender, "&7First Join: &b" + TimeUtils.formatTime(System.currentTimeMillis() - firstJoin) + " ago");
        sendMessage(sender, "&7Last Seen: &b" + TimeUtils.formatTime(System.currentTimeMillis() - lastJoin) + " ago");
        sendMessage(sender, "&7Playtime: &e" + TimeUtils.formatTime(playtime));
        
        // Progress bar XP
        long xpToNext = (level + 1) * 1000L;
        long currentLevelXP = experience - (level * 1000L);
        String xpBar = TextUtils.createProgressBar(currentLevelXP, 1000, 20, '█', '▒');
        sendMessage(sender, "&7XP Progress: &a" + xpBar + " &7(" + currentLevelXP + "/1000)");
        
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        sendMessage(sender, "");
    }
    
    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return SpigotPlayerUtils.getOnlinePlayerNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
```

---

## 🤝 Contribuire

Contributi sono benvenuti! Segui questi passi:

### 🍴 Fork e Setup

```bash
# Fork del repository
git clone https://github.com/tuousername/AminyoMCLib.git
cd AminyoMCLib

# Setup ambiente sviluppo
./gradlew build
```

### 🔧 Linee Guida

1. **Java 21+** - Usa le ultime feature di Java
2. **Codice Pulito** - Segui le convenzioni Java
3. **Test** - Aggiungi test per nuove feature
4. **Documentazione** - Documenta le API pubbliche
5. **Commit Convention** - Usa conventional commits

### 📝 Esempio Commit

```bash
feat(database): add Redis support for caching
fix(spigot): resolve compatibility issue with 1.7.10
docs(readme): update installation instructions
test(utils): add tests for TimeUtils parsing
```

### 🎯 Aree di Contributo

- 🐛 **Bug Fixes** - Risolvi problemi esistenti
- ✨ **Nuove Feature** - Aggiungi funzionalità
- 📚 **Documentazione** - Migliora guide e esempi  
- 🧪 **Testing** - Espandi la copertura test
- 🎨 **Performance** - Ottimizza il codice
- 🌍 **Localizzazione** - Traduci in altre lingue

---

## 🆘 Supporto

### 📞 Canali di Supporto

- 🐛 **Issues**: [GitHub Issues](https://github.com/aminyo/AminyoMCLib/issues)
- 💬 **Discord**: [Server Discord](https://discord.gg/aminyo)
- 📧 **Email**: support@aminyo.it
- 📖 **Wiki**: [Documentazione Completa](https://github.com/aminyo/AminyoMCLib/wiki)

### ❓ FAQ

<details>
<summary><strong>AminyoMCLib è compatibile con la mia versione di Minecraft?</strong></summary>

AminyoMCLib supporta versioni da 1.7.10 a 1.21.8 per Spigot/Paper, tutte le versioni di BungeeCord, e Velocity 3.0+. Controlla la tabella compatibilità sopra.
</details>

<details>
<summary><strong>Posso usare AminyoMCLib in progetti commerciali?</strong></summary>

Sì! AminyoMCLib è rilasciata sotto licenza MIT, che permette uso commerciale, modifica e distribuzione.
</details>

<details>
<summary><strong>Come posso migrare da altre librerie?</strong></summary>

Forniamo guide di migrazione per librerie popolari come HikariCP, SnakeYAML, etc. Controlla la sezione Wiki.
</details>

<details>
<summary><strong>AminyoMCLib supporta Kotlin?</strong></summary>

Sì! Essendo una libreria Java, è completamente compatibile con Kotlin e tutti i linguaggi JVM.
</details>

---

## 🎉 Ringraziamenti

Un ringraziamento speciale a:

- 🎯 **Spigot Team** - Per l'incredibile API
- 🚀 **Paper Team** - Per le ottimizzazioni performance
- 🌐 **BungeeCord/Velocity** - Per il supporto proxy
- 🛠️ **HikariCP** - Per il connection pooling
- 📊 **MongoDB** - Per il supporto NoSQL
- 🎨 **SnakeYAML** - Per il parsing YAML
- 💾 **H2/SQLite** - Per i database embedded
- 🧪 **JUnit** - Per il framework testing
- ❤️ **Community** - Per feedback e contributi

---

## 📜 Licenza

```
MIT License

Copyright (c) 2024 Aminyo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the
---

## 📋 Indice

- [✨ Caratteristiche](#-caratteristiche)
- [🎯 Piattaforme Supportate](#-piattaforme-supportate)
- [⚡ Installazione](#-installazione)
- [🚀 Guida Rapida](#-guida-rapida)
- [📚 Documentazione](#-documentazione)
- [🛠️ Moduli](#️-moduli)
- [💾 Database](#-database)
- [📁 File Manager](#-file-manager)
- [🎨 Utilities](#-utilities)
- [📖 Esempi](#-esempi)
- [🤝 Contribuire](#-contribuire)
- [📜 Licenza](#-licenza)

---

## ✨ Caratteristiche

### 🎯 **Multipiattaforma**
- ✅ **Spigot/Bukkit** (1.7.10 - 1.21.8)
- ✅ **Paper** (1.8+)
- ✅ **BungeeCord** (Tutte le versioni)
- ✅ **Velocity** (3.0+)
- ✅ **Waterfall** (Compatibile con BungeeCord)

### 🚀 **Funzionalità Avanzate**
- 🔥 **API Unificata** - Un'unica API per tutte le piattaforme
- 🗄️ **Database Manager** - MySQL, PostgreSQL, MongoDB, SQLite, H2, JSON
- 📁 **File Manager** - YAML, JSON, TOML, Properties con auto-reload
- 🎨 **Text Utils** - Colori hex, formattazione, progress bar
- ⚡ **Async/Sync** - Operazioni asincrone e sincrone
- 🧰 **Utilities** - Math, Time, Location, Player, Reflection
- 🎯 **Version Support** - Compatibilità tra versioni diverse

### 💡 **Developer Friendly**
- 📖 **Documentazione Completa**
- 🧪 **Esempi Pratici**
- 🔧 **Setup Semplice**
- 🚀 **Performance Ottimizzate**

---

## 🎯 Piattaforme Supportate

| Piattaforma | Versioni | Stato | Note |
|-------------|----------|-------|------|
| ![Spigot](https://img.shields.io/badge/Spigot-orange) | 1.7.10 - 1.21.8 | ✅ Completo | Tutte le funzionalità |
| ![Paper](https://img.shields.io/badge/Paper-red) | 1.8+ | ✅ Completo | Ottimizzazioni extra |
| ![Bukkit](https://img.shields.io/badge/Bukkit-blue) | 1.7.10+ | ✅ Completo | Compatibilità legacy |
| ![BungeeCord](https://img.shields.io/badge/BungeeCord-purple) | Tutte | ✅ Completo | Proxy features |
| ![Velocity](https://img.shields.io/badge/Velocity-cyan) | 3.0+ | 🚧 In sviluppo | Prossima versione |
| ![Waterfall](https://img.shields.io/badge/Waterfall-teal) | Tutte | ✅ Compatibile | Via BungeeCord API |

---

## ⚡ Installazione

### 📥 Maven

```xml
<dependency>
    <groupId>it.aminyo</groupId>
    <artifactId>aminyo-mclib</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 📥 Gradle

```groovy
implementation 'it.aminyo:aminyo-mclib:1.0.0'
```

### 📦 JAR Download

Scarica l'ultima versione da [GitHub Releases](https://github.com/aminyo/AminyoMCLib/releases)

---

## 🚀 Guida Rapida

### 🔧 Setup Base

#### **Per Spigot/Paper**

```java
public class MioPlugin extends JavaPlugin {
    private AminyoMCLib aminyoLib;
    
    @Override
    public void onEnable() {
        // Inizializza AminyoMCLib
        SpigotPlatformAdapter adapter = new SpigotPlatformAdapter(this);
        this.aminyoLib = AminyoMCLibImpl.initialize(adapter);
        
        getLogger().info("Plugin abilitato con AminyoMCLib!");
        
        // Esempio: Comando semplice
        registerCommand(new MioComando(this));
    }
    
    @Override
    public void onDisable() {
        if (aminyoLib != null) {
            aminyoLib.shutdown();
        }
    }
}
```

#### **Per BungeeCord**

```java
public class MioPlugin extends Plugin {
    private AminyoMCLib aminyoLib;
    
    @Override
    public void onEnable() {
        // Inizializza AminyoMCLib
        BungeePlatformAdapter adapter = new BungeePlatformAdapter(this);
        this.aminyoLib = AminyoMCLibImpl.initialize(adapter);
        
        getLogger().info("Plugin abilitato con AminyoMCLib!");
    }
}
```

### 💬 Primo Comando

```java
public class MioComando extends SpigotCommand {
    
    public MioComando(JavaPlugin plugin) {
        super(plugin, "test", "mioplugin.test", "Comando di test");
    }
    
    @Override
    protected void execute(CommandSender sender, String[] args) {
        // Messaggio colorato
        sendMessage(sender, "&aHello &b{player}&a!", 
            Map.of("player", sender.getName()));
        
        // Info piattaforma
        AminyoMCLib lib = AminyoMCLibImpl.getInstance();
        sendMessage(sender, "&7Platform: &f" + lib.getPlatformAdapter().getPlatformType());
        sendMessage(sender, "&7Version: &f" + lib.getPlatformAdapter().getMinecraftVersion());
    }
}
```

---

## 📚 Documentazione

### 🏗️ Architettura

AminyoMCLib è costruita con un'architettura modulare che separa le funzionalità specifiche della piattaforma dall'API comune:

```
AminyoMCLib
├── 🏛️ Core API (Interfaces comuni)
├── 🎯 Platform Adapters (Spigot, BungeeCord, etc.)
├── 🗄️ Database Module (MySQL, MongoDB, JSON, etc.)
├── 📁 File Module (YAML, JSON, TOML, Properties)
└── 🧰 Utils Module (Text, Math, Time, Player, etc.)
```

### 🔌 Platform Adapter

Il `PlatformAdapter` fornisce un'interfaccia unificata per tutte le piattaforme:

```java
public interface PlatformAdapter {
    PlatformType getPlatformType();
    String getMinecraftVersion();
    CompletableFuture<Void> runAsync(Runnable task);
    CompletableFuture<Void> runSync(Runnable task);
    String getDataFolder();
    boolean supportsColors();
}
```

---

## 🛠️ Moduli

### 📊 Panoramica Moduli

| Modulo | Descrizione | Stato |
|--------|-------------|-------|
| **Core** | API base e gestione piattaforma | ✅ |
| **Database** | Gestione database multipli | ✅ |
| **File** | Manager per file di configurazione | ✅ |
| **Utils** | Utilities comuni per sviluppo | ✅ |
| **Command** | Framework per comandi avanzato | ✅ |
| **Event** | Sistema eventi cross-platform | 🚧 |
| **GUI** | Menu e interfacce grafiche | 🚧 |

---

## 💾 Database

### 🗄️ Database Supportati

AminyoMCLib supporta una vasta gamma di database:

| Database | Tipo | Driver | Esempio |
|----------|------|--------|---------|
| **MySQL** | SQL | `mysql-connector-j` | Server produzione |
| **MariaDB** | SQL | `mariadb-java-client` | Alternative a MySQL |
| **PostgreSQL** | SQL | `postgresql` | Database enterprise |
| **SQLite** | SQL | `sqlite-jdbc` | Database locale |
| **H2** | SQL | `h2` | Testing e sviluppo |
| **MongoDB** | NoSQL | `mongodb-driver` | Documenti JSON |
| **JSON** | File | Built-in | Configurazioni semplici |

### ⚙️ Configurazione Database

```java
// Configurazione MySQL
DatabaseConfig config = new DatabaseConfig(DatabaseType.MYSQL);
config.setHost("localhost");
config.setPort(3306);
config.setDatabase("minecraft");
config.setUsername("user");
config.setPassword("password");
config.setMaxPoolSize(20);

// Creazione manager
DatabaseModule dbModule = new DatabaseModule(getDataFolder());
DatabaseManager dbManager = dbModule.createDatabaseManager("main", config);
```

### 💡 Esempi Database

#### **Query SQL Sincrone**

```java
// Esecuzione query
QueryResult result = dbManager.executeQuery(
    "SELECT * FROM players WHERE uuid = ?", 
    playerUuid
);

// Lettura risultati
for (Map<String, Object> row : result.getRows()) {
    String name = (String) row.get("name");
    int level = (Integer) row.get("level");
    // Processa dati...
}
```

#### **Operazioni Asincrone**

```java
// Query asincrona
dbManager.executeQueryAsync("SELECT * FROM stats WHERE player_id = ?", playerId)
    .thenAccept(result -> {
        // Processa risultati in thread separato
        updatePlayerStats(result);
    })
    .thenRunAsync(() -> {
        // Torna al thread principale per operazioni Bukkit
        player.sendMessage("§aStats updated!");
    }, bukkitScheduler::runTask);
```

#### **MongoDB Operations**

```java
MongoDatabaseManager mongoManager = (MongoDatabaseManager) dbManager;

// Inserimento documento
Document player = new Document("uuid", playerUuid)
    .append("name", playerName)
    .append("level", 10)
    .append("lastLogin", new Date());

mongoManager.insertDocument("players", player)
    .thenRun(() -> logger.info("Player saved to MongoDB"));

// Ricerca documenti
Document filter = new Document("level", new Document("$gte", 50));
mongoManager.findDocuments("players", filter)
    .thenAccept(documents -> {
        logger.info("Found {} high-level players", documents.size());
    });
```

---

## 📁 File Manager

### 📝 Formati Supportati

- **YAML** (.yml, .yaml) - Configurazioni readable
- **JSON** (.json) - Dati strutturati
- **TOML** (.toml) - Tom's Obvious, Minimal Language  
- **Properties** (.properties) - Formato Java standard

### 🔧 Utilizzo File Manager

```java
FileManager fileManager = new FileManager(getDataFolder());

// Carica file YAML
YamlFileModel config = fileManager.getYamlFile("config.yml");

// Lettura valori
String serverName = config.getString("server.name", "Default Server");
int maxPlayers = config.getInt("server.maxPlayers", 100);
List<String> admins = config.getStringList("server.admins");

// Scrittura valori
config.set("server.lastRestart", System.currentTimeMillis());
config.set("stats.totalLogins", config.getInt("stats.totalLogins", 0) + 1);

// Auto-save abilitato
config.setAutoSave(true); // Salva automaticamente ad ogni modifica
```

### 🎛️ Configurazione Avanzata

```java
// File JSON per dati complessi
JsonFileModel playerData = fileManager.getJsonFile("players.json");

// Struttura dati nidificata
Map<String, Object> playerInfo = Map.of(
    "uuid", player.getUniqueId().toString(),
    "stats", Map.of(
        "kills", 10,
        "deaths", 3,
        "playtime", 3600000L
    ),
    "locations", Map.of(
        "home", Map.of(
            "world", "world",
            "x", 100.5,
            "y", 64.0,
            "z", 200.3
        )
    )
);

playerData.set("players." + player.getName(), playerInfo);
```

---

## 🎨 Utilities

### 🌈 Text Utils

AminyoMCLib offre potenti utilities per la gestione del testo:

```java
// Colori hex (1.16+) e legacy
String message = TextUtils.colorize("&aHello &#ff5555World&r!");

// Progress bar personalizzate
String progressBar = TextUtils.createProgressBar(75, 100, 20, '█', '░');
// Output: "███████████████░░░░░"

// Centratura testo
String centeredTitle = TextUtils.center("=== WELCOME ===", 50, ' ');

// Wrap testo
List<String> wrappedLines = TextUtils.wrapText(longText, 40);

// Placeholder sostituzione
String welcome = TextUtils.replacePlaceholders(
    "Welcome {player} to {server}!",
    Map.of(
        "player", player.getName(),
        "server", "MyServer"
    )
);
```

### ⏰ Time Utils

```java
// Parsing tempo human-friendly
long duration = TimeUtils.parseTime("1h 30m 45s"); // milliseconds

// Formattazione durata
String formatted = TimeUtils.formatTime(duration); // "1h 30m 45s"

// Timestamps
String timestamp = TimeUtils.getCurrentTimestamp(); // "2024-01-15 14:30:25"
String custom = TimeUtils.getCurrentTimestamp("dd/MM/yyyy HH:mm");

// Controllo scadenza
boolean expired = TimeUtils.hasElapsed(lastLoginTime, Duration.ofDays(30).toMillis());
```

### 🧮 Math Utils

```java
// Clamping valori
int health = MathUtils.clamp(playerHealth, 0, 20);
double damage = MathUtils.clamp(baseDamage * multiplier, 0.0, 999.9);

// Numeri random
int randomLevel = MathUtils.randomInt(1, 100);
double randomMultiplier = MathUtils.randomDouble(0.8, 1.2);

// Percentuali
double percentage = MathUtils.percentage(currentXP, maxXP); // 0.0 - 1.0

// Formattazione numeri
String formatted = MathUtils.formatNumber(123456.789, 2); // "123456.79"
```

### 📍 Location Utils

```java
// Location cross-platform
SimpleLocation loc = new SimpleLocation("world", 100.5, 64, 200.3, 180f, 0f);

// Calcolo distanze
double distance = LocationUtils.distance(loc1, loc2);
double distance2D = LocationUtils.distance2D(x1, z1, x2, z2);

// Parsing da stringa
SimpleLocation parsed = LocationUtils.parseLocation("world:100,64,200:180,0");

// Area checking
boolean inArea = LocationUtils.isWithinArea(playerLoc, corner1, corner2);
```

### 👤 Player Utils

```java
// Validazione nomi
boolean validName = PlayerUtils.isValidPlayerName("Aminyo"); // true
boolean validUUID = PlayerUtils.isValidUUID(uuid.toString());

// UUID formatting
String formatted = PlayerUtils.formatUUID(trimmedUuid);
String trimmed = PlayerUtils.trimUUID(formattedUuid);

// Per Spigot - utilities specifiche
Player player = SpigotPlayerUtils.getPlayer("Aminyo");
SpigotPlayerUtils.sendMessage(player, "&aWelcome back!");
SpigotPlayerUtils.broadcast("&6Server restarting in 5 minutes!");
```

---

## 📖 Esempi

### 🏆 Plugin Completo di Esempio

```java
public class ExamplePlugin extends SpigotAminyoPlugin {
    
    private FileManager fileManager;
    private DatabaseModule databaseModule;
    
    @Override
    public void onEnable() {
        super.onEnable(); // Inizializza AminyoMCLib
        
        // Setup file manager
        this.fileManager = new FileManager(getDataFolder().getAbsolutePath());
        
        // Carica configurazione
        YamlFileModel config = fileManager.getYamlFile("config.yml");
        setupDefaultConfig(config);
        
        // Setup database se abilitato
        if (config.getBoolean("database.enabled", false)) {
            setupDatabase(config);
        }
        
        // Registra comandi e listener
        registerCommands();
        registerListeners();
        
        getLogger().info("ExamplePlugin abilitato!");
    }
    
    private void setupDefaultConfig(YamlFileModel config) {
        // Valori di default
        Map<String, Object> defaults = Map.of(
            "plugin.name", "ExamplePlugin",
            "plugin.prefix", "&8[&6Example&8] ",
            "database.enabled", false,
            "database.type", "SQLITE",
            "database.file", "data.db",
            "features.welcomeMessage", true,
            "features.autoSave", true,
            "messages.welcome", "&aWelcome {player} to the server!",
            "messages.goodbye", "&cGoodbye {player}!"
        );
        
        defaults.forEach((key, value) -> {
            if (!config.contains(key)) {
                config.set(key, value);
            }
        });
        
        config.save();
    }
    
    private void setupDatabase(YamlFileModel config) {
        this.databaseModule = new DatabaseModule(getDataFolder().getAbsolutePath());
        databaseModule.enable();
        
        // Configura database
        DatabaseConfig dbConfig = new DatabaseConfig(
            DatabaseType.valueOf(config.getString("database.type", "SQLITE"))
        );
        
        if (dbConfig.getType().isFile()) {
            dbConfig.setFilePath(config.getString("database.file", "data.db"));
        } else {
            dbConfig.setHost(config.getString("database.host", "localhost"));
            dbConfig.setPort(config.getInt("database.port", 3306));
            dbConfig.setDatabase(config.getString("database.name", "minecraft"));
            dbConfig.setUsername(config.getString("database.username", "root"));
            dbConfig.setPassword(config.getString("database.password", ""));
        }
        
        try {
            DatabaseManager dbManager = databaseModule.createDatabaseManager("main", dbConfig);
            
            // Crea tabelle se necessario
            createTables(dbManager);
            
            getLogger().info("Database connesso: " + dbConfig.getType());
        } catch (Exception e) {
            getLogger().severe("Errore connessione database: " + e.getMessage());
        }
    }
    
    private void createTables(DatabaseManager dbManager) throws Exception {
        // Tabella giocatori
        dbManager.executeUpdate("""
            CREATE TABLE IF NOT EXISTS players (
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                uuid VARCHAR(36) UNIQUE NOT NULL,
                name VARCHAR(16) NOT NULL,
                first_join BIGINT NOT NULL,
                last_join BIGINT NOT NULL,
                total_playtime BIGINT DEFAULT 0,
                level INTEGER DEFAULT 1,
                experience BIGINT DEFAULT 0
            )
        """);
        
        // Indici per performance
        dbManager.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_uuid ON players(uuid)");
        dbManager.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_name ON players(name)");
    }
    
    private void registerCommands() {
        registerCommand(new InfoCommand(this));
        registerCommand(new StatsCommand(this));
        registerCommand(new ReloadCommand(this));
    }
    
    private void registerListeners() {
        registerListener(new PlayerJoinListener(this));
        registerListener(new PlayerQuitListener(this));
    }
}
```

### 🎯 Comando Avanzato

```java
public class StatsCommand extends SpigotCommand {
    
    private final ExamplePlugin plugin;
    private final DatabaseManager dbManager;
    
    public StatsCommand(ExamplePlugin plugin) {
        super(plugin, "stats", "example.stats", "View player statistics");
        this.plugin = plugin;
        this.dbManager = plugin.getDatabaseModule().getDatabaseManager("main");
    }
    
    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!isPlayer(sender)) {
            sendMessage(sender, "&cThis command can only be used by players!");
            return;
        }
        
        Player player = getPlayer(sender);
        String targetPlayer = args.length > 0 ? args[0] : player.getName();
        
        // Query asincrona per non bloccare il server
        dbManager.executeQueryAsync(
            "SELECT * FROM players WHERE name = ? OR uuid = ?",
            targetPlayer, targetPlayer
        ).thenAccept(result -> {
            
            if (result.isEmpty()) {
                sendMessage(sender, "&cPlayer not found: " + targetPlayer);
                return;
            }
            
            Map<String, Object> data = result.getFirstRow();
            
            // Torna al thread principale per inviare il messaggio
            plugin.getScheduler().runSync(() -> {
                sendStatsMessage(sender, data);
            });
            
        }).exceptionally(throwable -> {
            plugin.getLogger().severe("Error querying player stats: " + throwable.getMessage());
            sendMessage(sender, "&cError retrieving stats!");
            return null;
        });
    }
    
    private void sendStatsMessage(CommandSender sender, Map<String, Object> data) {
        String name = (String) data.get("name");
        long firstJoin = (Long) data.get("first_join");
        long lastJoin = (Long) data.get("last_join");
        long playtime = (Long) data.get("total_playtime");
        int level = (Integer) data.get("level");
        long experience = (Long) data.get("experience");
        
        // Header decorativo
        sendMessage(sender, "");
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        sendMessage(sender, TextUtils.center("&6&lPLAYER STATS", 30, ' '));
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        
        // Statistiche
        sendMessage(sender, "");
        sendMessage(sender, "&7Player: &f" + name);
        sendMessage(sender, "&7Level: &a" + level + " &8(&7" + experience + " XP&8)");
        sendMessage(sender, "&7First Join: &b" + TimeUtils.formatTime(System.currentTimeMillis() - firstJoin) + " ago");
        sendMessage(sender, "&7Last Seen: &b" + TimeUtils.formatTime(System.currentTimeMillis() - lastJoin) + " ago");
        sendMessage(sender, "&7Playtime: &e" + TimeUtils.formatTime(playtime));
        
        // Progress bar XP
        long xpToNext = (level + 1) * 1000L;
        long currentLevelXP = experience - (level * 1000L);
        String xpBar = TextUtils.createProgressBar(currentLevelXP, 1000, 20, '█', '▒');
        sendMessage(sender, "&7XP Progress: &a" + xpBar + " &7(" + currentLevelXP + "/1000)");
        
        sendMessage(sender, "&6&l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        sendMessage(sender, "");
    }
    
    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return SpigotPlayerUtils.getOnlinePlayerNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
```

---

## 🤝 Contribuire

Contributi sono benvenuti! Segui questi passi:

### 🍴 Fork e Setup

```bash
# Fork del repository
git clone https://github.com/tuousername/AminyoMCLib.git
cd AminyoMCLib

# Setup ambiente sviluppo
./gradlew build
```

### 🔧 Linee Guida

1. **Java 21+** - Usa le ultime feature di Java
2. **Codice Pulito** - Segui le convenzioni Java
3. **Test** - Aggiungi test per nuove feature
4. **Documentazione** - Documenta le API pubbliche
5. **Commit Convention** - Usa conventional commits

### 📝 Esempio Commit

```bash
feat(database): add Redis support for caching
fix(spigot): resolve compatibility issue with 1.7.10
docs(readme): update installation instructions
test(utils): add tests for TimeUtils parsing
```

### 🎯 Aree di Contributo

- 🐛 **Bug Fixes** - Risolvi problemi esistenti
- ✨ **Nuove Feature** - Aggiungi funzionalità
- 📚 **Documentazione** - Migliora guide e esempi  
- 🧪 **Testing** - Espandi la copertura test
- 🎨 **Performance** - Ottimizza il codice
- 🌍 **Localizzazione** - Traduci in altre lingue

---

## 🆘 Supporto

### 📞 Canali di Supporto

- 🐛 **Issues**: [GitHub Issues](https://github.com/aminyo/AminyoMCLib/issues)
- 💬 **Discord**: [Server Discord](https://discord.gg/aminyo)
- 📧 **Email**: support@aminyo.it
- 📖 **Wiki**: [Documentazione Completa](https://github.com/aminyo/AminyoMCLib/wiki)

### ❓ FAQ

<details>
<summary><strong>AminyoMCLib è compatibile con la mia versione di Minecraft?</strong></summary>

AminyoMCLib supporta versioni da 1.7.10 a 1.21.8 per Spigot/Paper, tutte le versioni di BungeeCord, e Velocity 3.0+. Controlla la tabella compatibilità sopra.
</details>

<details>
<summary><strong>Posso usare AminyoMCLib in progetti commerciali?</strong></summary>

Sì! AminyoMCLib è rilasciata sotto licenza MIT, che permette uso commerciale, modifica e distribuzione.
</details>

<details>
<summary><strong>Come posso migrare da altre librerie?</strong></summary>

Forniamo guide di migrazione per librerie popolari come HikariCP, SnakeYAML, etc. Controlla la sezione Wiki.
</details>

<details>
<summary><strong>AminyoMCLib supporta Kotlin?</strong></summary>

Sì! Essendo una libreria Java, è completamente compatibile con Kotlin e tutti i linguaggi JVM.
</details>

---

## 🎉 Ringraziamenti

Un ringraziamento speciale a:

- 🎯 **Spigot Team** - Per l'incredibile API
- 🚀 **Paper Team** - Per le ottimizzazioni performance
- 🌐 **BungeeCord/Velocity** - Per il supporto proxy
- 🛠️ **HikariCP** - Per il connection pooling
- 📊 **MongoDB** - Per il supporto NoSQL
- 🎨 **SnakeYAML** - Per il parsing YAML
- 💾 **H2/SQLite** - Per i database embedded
- 🧪 **JUnit** - Per il framework testing
- ❤️ **Community** - Per feedback e contributi

---

## 📜 Licenza

```
MIT License

Copyright (c) 2024 Aminyo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the
