import json

with open("team_players.json", "r") as file1, open("teams.json", "r") as file2:
    team_players = json.load(file1)
    teams = json.load(file2)
    players_json = {}
    id = 0

    for key in team_players:
        players = team_players[key]
        for player in players:
            player_json = {}
            player_json["id"] = id            
            player_json["name"] = player
            team_id = -1
            for k, v in teams.items():                 
                if v["name"] == key:                    
                    team_id = int(k)                   
                    break
            assert team_id >= 0
            player_json["teamId"] = team_id
            players_json[player_json["id"]] = player_json
            id += 1
    with open("players.json", "w") as of:
        json.dump(players_json, of, indent=4)
   