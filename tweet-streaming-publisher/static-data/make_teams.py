import json

with open("team_players.json", "r") as file1, open("players.json", "r") as file2, open("team_hashtags.json", "r") as file3:
    data = json.load(file1)
    players = json.load(file2)
    hashtags = json.load(file3)
    teams = {}    
    id = 0
    for key in data:
        team = {}
        team["id"] = id
        team["name"] = key
        team["playersId"] = []
        for player in data[key]:
            player_id = -1
            for k, v in players.items():
                if v["name"] == player:
                    player_id = k
                    break
            assert player_id >= 0
            team["playersId"].append(player_id)
        team["keywords"] = []  
        team["keywords"].append(hashtags[key])
        team["keywords"].append(key.split()[-1])
        team["keywords"].append(key.replace(" ", ""))
        for player in data[key]:
            team["keywords"].append(player)
            team["keywords"].append(player.replace(" ", "").replace("-", "").replace(".", ""))
        teams[id] = team
        id += 1
    with open("teams.json", "w") as of:
        json.dump(teams, of, indent=4)
