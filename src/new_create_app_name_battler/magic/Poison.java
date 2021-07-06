package new_create_app_name_battler.magic;

import new_create_app_name_battler.party.IPlayer;

public class Poison extends BaseUseMagic{

  @Override
  public int effect(IPlayer attacker, IPlayer defender) {

    super.effect(attacker, defender);

    System.out.printf("%sは%sを唱えた！\n瘴気が相手を包んだ！\n", attacker.getName(), magicData.getName());
    defender.setPoison(true);// 相手に毒をセット
    System.out.printf("%sは毒状態になった！\n", defender.getName());

    attacker.downMp(this.magicData.getMpcost());

    return damage;

  }

  @Override
  public void initMagic(){
    this.magicData = MagicData.POISON;

  }

}