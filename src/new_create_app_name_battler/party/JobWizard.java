package new_create_app_name_battler.party;

import new_create_app_name_battler.magic.IMagicalUsable;
import new_create_app_name_battler.magic.Magic;

public class JobWizard extends BasePlayer implements IMagicalUsable, IWizard {

  /**
   * コンストラクタ
   * @param name : プレイヤー名
   */
  public JobWizard(String name) {

    super(name);
  }

  @Override
  public void initJob(){
    jobData = JobData.WIZARD;
  }

  @Override
  public void normalAttack(BasePlayer defender) {

    attackType = "A";
    System.out.printf("%sの攻撃！\n%sは杖を投げつけた！\n", getName(), getName());
    damage = calcDamage(defender);// 与えるダメージを求める
    damageProcess(attackType, this, defender, damage);
    knockedDownCheck(defender);
  }

  @Override
  public void skillAttack(BasePlayer defender) {

    attackType = "M";

    if (random.nextInt(100) + 1 <= Magic.FIREELEMENTAL.getInvocationRate()) {// 25％で発動

      System.out.printf("%sは魔法陣を描いて%sを召還した\n%sの攻撃！\n", getName(), Magic.FIREELEMENTAL.getName(),
          Magic.FIREELEMENTAL.getName());

      super.damageProcess(attackType, this, defender, Magic.FIREELEMENTAL.getMinDamage());// ダメージ処理

    } else {// 75%で不発

      System.out.printf("%sの攻撃だがスキルは発動しなかった！\n", getName());
    }
    knockedDownCheck(defender);
  }

  @Override
  public void magicAttack(BasePlayer defender) {


    if (hasEnoughMp()) {

      attackType = "M";
      damage = effect(defender);
      super.damageProcess(attackType, this, defender, damage);
      knockedDownCheck(defender);

    } else {

      System.out.printf("%sは魔法を唱えようとしたが、MPが足りない！！\n", getName());
      normalAttack(defender);
    }

  }

  @Override
  public void eatGrass() {
    super.eatGrass();
    knockedDownCheck(this);
  }

  @Override
  public int effect(BasePlayer defender) {

    if (20 <= getMp()) {// MPが20以上の場合

      if (random.nextInt(2) == 0) {// 0の場合サンダーを使用

        damage = useThunder();

      } else {// 1の場合ファイアを使用

        damage = useFire();
      }

    } else if (10 <= this.getMp() && this.getMp() < 20) {// MPが10以上20未満の場合ファイアを使用する

      damage = useFire();// ファイアを使用
    }

    return damage;
  }

  private int useThunder() {

    System.out.printf("%sは%sを唱えた！\n雷が地面を這っていく！\n", getName(), Magic.THUNDER.getName());

    damage =
        random.nextInt(Magic.THUNDER.getMaxDamage() - Magic.THUNDER.getMinDamage())
            + Magic.THUNDER.getMinDamage();// 乱数20～50

    this.mp = this.getMp() - Magic.THUNDER.getMpcost();// MPを消費

    return damage;
  }

  private int useFire() {
    System.out.printf("%sは%sを唱えた！\n炎が渦を巻いた！\n", getName(), Magic.FIRE.getName());

    damage =
        random.nextInt(Magic.FIRE.getMaxDamage() - Magic.FIRE.getMinDamage())
            + Magic.FIRE.getMinDamage();// 乱数10～30

    this.mp = this.getMp() - Magic.FIRE.getMpcost();// MPを消費

    return damage;
  }

  @Override
  public boolean hasEnoughMp() {

    if (10 <= this.getMp()) {

      return true;

    } else {

      return false;
    }
  }
}
