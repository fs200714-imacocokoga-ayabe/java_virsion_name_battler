package new_create_app_name_battler.strategy;

import java.util.List;
import new_create_app_name_battler.party.BasePlayer;

public class EnemyPriestStrategy extends StrategyEnemyPattern {

  @Override
  public int attackStrategy(BasePlayer player1, List<BasePlayer> playerParty,
      List<BasePlayer> aiParty) {

    this.playerParty.addAll(playerParty);
    this.aiParty.addAll(aiParty);
    this.player2 = selectLowerHP();// HPの低い相手を呼び出す
    // player = lifeImpotance();// HPの低い味方を入れる

    if (player1.isPoison()) {// playerが毒状態の場合

      player1.eatGrass();

    } else if (player1.getHp() < (player1.getMaxHp() / 4)) {

      player1.eatGrass();

    } else if (PRIEST_MP <= player1.getMp()) {

      player1.magicAttack(player2);// 魔法を使う

    } else if (player2.getDef() < (player1.getStr() / 2)) {

      player1.normalAttack(player2);

    } else {

      player1.skillAttack(player2);

    }

    id = player2.getIdNumber();

    return id;
  }

  BasePlayer selectLowerHP() {


    player2 = playerParty.get(0);// 敵パーティから1人player2に入れる

    for (int i = 1; i < playerParty.size(); i++) {

      if (playerParty.get(i).getHp() < player2.getHp()) {// player2よりHPが低い場合

        player2 = playerParty.get(i);// HPのひくい敵をplayer2に入れる

      }
    }

    playerParty.clear();

    return player2;

  }

  BasePlayer lifeImpotance() {

    for (int i = 0; i < aiParty.size(); i++) {

      if (aiParty.get(i).getHp() < player1.getHp()) {

        player = aiParty.get(i);

      }
    }

    aiParty.clear();

    return player;

  }

}
