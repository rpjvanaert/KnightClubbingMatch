# Design of KnightClubbingMatch

KnightClubbingMatch is a GUI/Server application designed to manage and play KnightClubbing/UCI matches.

# Overview
- GUI
  - Functional
    - SPRT
    - Graph/table results
    - PGN's
    - Time Control
    - Engine settings
    - Visualize (board, moves)
  - Technical
    - Remote run
    - Local run
    - Engine management
- Server
  - Match management/run
    - REST API
    - WebSockets for spectating live matches
  - SPRT calculation
  - UCI
  - Database
    - Match history
    - Engine settings
  - Engines from Maven Repository
- Shared
  - Protocol
  - Data structures
  - Utilities

# Communication
Communication between GUI and Server is done via a REST API and WebSockets. The GUI sends requests to the server, which processes them and returns the results. WebSockets are used for real-time updates, such as live match status and results.

## API Endpoints
- `/api/run` - Run a match with specified parameters.
- `/api/status` - Get the status of server: busy, idle, etc.
- `/api/history` - Retrieve match history.
- `/api/history/match/{id}` - Retrieve a specific match by ID.
- `/api/history/game/{id}` - Retrieve a specific game by ID.
- `/api/engines/add` - Validate and add a new engine.
- `/api/engines` - Retrieve list of available engines.
- `/api/engines/{id}` - Retrieve details of a specific engine.

### Endpoint: Run
- Method: POST
- Description: Run a match with specified parameters.
- Request Body: JSON object containing match parameters, example:
  - engine1: "engine1_id"
  - engine2: "engine2_id"
  - timeControl: "5+3"
  - amount: 10
  - uciOptions: { "option1": "value1", "option2": "value2" }
  - sprt: true
- Response: JSON object with match ID and status, example:
  - matchId: "12345"
  - status: "success"

### Endpoint: Status
- Method: GET
- Description: Get the current status of the server.
- Response: JSON object with status information, example:
  - status: "idle"

### Endpoint: History
- Method: GET
- Description: Retrieve match history. Maximum 1000 matches.
- Headers:
  - from (optional): "2023-01-01T00:00:00Z"
  - to (optional): "2023-12-31T23:59:59Z"
- Response: JSON array of match objects, each containing:
  - id: "match_id"
  - engine1: "engine1_id"
  - engine2: "engine2_id"
  - timeControl: "5+3"
  - result: "24-76"
  - status: "completed"
  - completedAt: "2023-10-01T12:00:00Z"
  - uciOptions: { "option1": "value1", "option2": "value2" }

### Endpoint: History Match
- Method: GET
- Description: Retrieve a specific match by ID.
- Path Parameter: `id` - Match ID
- Response: JSON object with match details, example:
  - id: "match_id"
  - engine1: "engine1_id"
  - engine2: "engine2_id"
  - timeControl: "5+3"
  - result: "24-76"
  - status: "completed"
  - completedAt: "2023-10-01T12:00:00Z"
  - games: [
    {
    id: "game_id",
    result: "1-0",
    completedAt: "2023-10-01T12:05:00Z"
    },
    ...
    ]
  - sprt: {
    state: "passed",
    elo0: 0,
    elo1: 5,
    elo: 3.2,
    alpha: 0.05,
    beta: 0.05,
    lower_bound: -2.94,
    upper_bound: 2.94
    }

### Endpoint: History Game
- Method: GET
- Description: Retrieve a specific game by ID.
- Path Parameter: `id` - Game ID
- Response: JSON object with game details, example:
  - id: "game_id"
  - matchId: "match_id"
  - result: "1-0"
  - pgn: "pgn"
  - completedAt: "2023-10-01T12:05:00Z"

### Endpoint: Engines Add
- Method: POST
- Description: Validate and add a new engine.
- Request Body: JSON object containing engine details, example:
  - name: "Engine Name"
  - id: "engine_id"
  - availble_options: ["option1", "option2"]
  - mvn_repository: "https://example.com/engine.jar"
- Response: JSON object with status, example:
  - status: "success"
  - message: "Engine added successfully"

### Endpoint: Engines
- Method: GET
- Description: Retrieve list of available engines.
- Response: JSON array of engine objects, each containing:
  - id: "engine_id"
  - name: "Engine Name"

### Endpoint: Engines ID
- Method: GET
- Description: Retrieve details of a specific engine by ID.
- Path Parameter: `id` - Engine ID
- Response: JSON object with engine details, example:
  - id: "engine_id"
  - name: "Engine Name"
  - available_options: ["option1", "option2"]

## WebSocket Communication
- `/ws/results/{id}` - Subscribe to live match results.
- `/ws/watch/{id}` - Watch a live match.