package dao;

import java.util.Scanner;

import dto.BatterDto;
import dto.HumanDto;
import dto.PitcherDto;
import file.DataProc;

public class BaseballDaoImpl implements BaseballDao {

  Scanner sc = new Scanner(System.in);

  private HumanDto human[] = { new PitcherDto(1001, "홍길동", 24, 172.1, "투수", 10, 3, 0.234), new BatterDto(1002, "성춘향", 20, 172.1, "타자", 15, 5, 0.312),
      new BatterDto(1003, "일지매", 22, 181.1, "투수", 9, 4, 0.876), new BatterDto(1004, "홍길동", 26, 191.6, "타자", 20, 6, 0.253),
      new PitcherDto(1005, "홍두께", 25, 175.2, "투수", 9, 6, 0.356), new PitcherDto(1006, "임꺽정", 26, 173.8, "투수", 12, 5, 0.412) };
  private int counter;

  DataProc dataProc;

  public BaseballDaoImpl() {
    human = new HumanDto[10];
    counter = 6;

    dataProc = new DataProc("baseball");
    dataProc.createFile();
  }

  @Override
  public void insert() {
    System.out.println("선수를 등록합니다");
    System.out.print("등록할 포지션: 투수(1)/타자(2) >> ");
    int position = sc.nextInt();

    System.out.print("선수번호 >> ");
    int number = sc.nextInt();

    System.out.print("선수이름 >> ");
    String name = sc.next();

    System.out.print("선수나이 >> ");
    int age = sc.nextInt();

    System.out.print("선수신장 >> ");
    double height = sc.nextDouble();

    HumanDto humanDto = null;
    if (position == 1) {
      System.out.print("승 >> ");
      int win = sc.nextInt();

      System.out.print("패 >> ");
      int lose = sc.nextInt();

      System.out.print("방어율 >> ");
      double defence = sc.nextDouble();

      humanDto = new PitcherDto(number, name, age, height, "투수", win, lose, defence);
    } else {
      System.out.print("타수 >> ");
      int batCount = sc.nextInt();

      System.out.print("히트수 >> ");
      int hit = sc.nextInt();

      System.out.print("타율 >> ");
      double hitAvg = sc.nextDouble();

      humanDto = new BatterDto(number, name, age, height, "타자", batCount, hit, hitAvg);
    }

    human[counter] = humanDto;
    counter++;

    System.out.println("등록했습니다");
  }

  @Override
  public void delete() {
    System.out.println("선수를 삭제합니다");
    System.out.print("삭제할 선수의 이름 >> ");
    String name = sc.next();

    int index = search(name);

    if (index == -1) {
      System.out.println("선수명단에 없습니다");
      return;
    }
    // human[index] = null;
    human[index].setNumber(0);
    human[index].setName("");
    human[index].setAge(0);
    human[index].setHeight(0.0);

    if (human[index] instanceof PitcherDto) {
      PitcherDto p = (PitcherDto) human[index];
      p.setPosition("");
      p.setWin(0);
      p.setLose(0);
      p.setDefence(0.0);
    } else {
      BatterDto b = (BatterDto) human[index];
      b.setPosition("");
      b.setBatCount(0);
      b.setHit(0);
      b.setHitAvg(0.0);
    }
  }

  @Override
  public void select() {
    System.out.println("선수를 검색합니다");
    System.out.print("검색할 선수의 이름 >> ");
    String name = sc.next();

    // 검색된 선수를 카운트
    int count = 0;
    for (int i = 0; i < human.length; i++) {
      HumanDto h = human[i];
      if (h != null && !h.getName().equals("")) {
        if (name.equals(h.getName())) {
          count++;
        }
      }
    }

    if (count == 0) {
      System.out.println("선수명단에 없습니다");
      return;
    }

    // 1명 이상일 때 배열 확보
    HumanDto findHuman[] = new HumanDto[count];
    int c = 0;
    for (int i = 0; i < human.length; i++) {
      HumanDto h = human[i];
      if (h != null && !h.getName().equals("")) {
        if (name.equals(h.getName())) {
          findHuman[c] = human[i];
          c++;
        }
      }
    }
    System.out.println("선수 명단입니다");
    for (int i = 0; i < findHuman.length; i++) {
      System.out.println(findHuman[i].toString());
    }
  }

  @Override
  public void update() {
    System.out.println("선수의 정보를 수정합니다");
    System.out.print("수정할 선수의 이름 >> ");
    String name = sc.next();

    int index = this.search(name);

    if (index == -1) {
      System.out.println("선수명단에 없습니다");
      return;
    }

    if (human[index] instanceof PitcherDto) {
      System.out.print("승 >> ");
      int win = sc.nextInt();

      System.out.print("패 >> ");
      int lose = sc.nextInt();

      System.out.print("방어율 >> ");
      double defence = sc.nextDouble();

      PitcherDto p = (PitcherDto) human[index];
      p.setWin(0);
      p.setLose(0);
      p.setDefence(0.0);
    } else {
      System.out.print("타수 >> ");
      int batCount = sc.nextInt();

      System.out.print("히트수 >> ");
      int hit = sc.nextInt();

      System.out.print("타율 >> ");
      double hitAvg = sc.nextDouble();

      BatterDto b = (BatterDto) human[index];
      b.setBatCount(0);
      b.setHit(0);
      b.setHitAvg(0.0);
    }

    System.out.println("수정했습니다");
  }

  @Override
  public void allPrint() {
    for (HumanDto h : human) {
      if (h != null && !h.getName().equals("")) {
        System.out.println(h.toString());
      }
    }

  }

  @Override
  public void batSort() {

    HumanDto humanB[] = new HumanDto[10];

    // 타자만으로 (배열)구성
    int count = 0;
    for (int i = 0; i < human.length; i++) {
      HumanDto h = human[i];
      if (h != null && h.getName().equals("") == false) {
        if (h instanceof BatterDto) {
          humanB[count] = h;
          count++;
        }
      }
    }

    for (int i = 0; i < humanB.length; i++) {
      if (humanB[i] != null) {
        // System.out.println(h.info());
        System.out.println((i + 1) + "위" + " 이름:" + humanB[i].getName() + " 타율:" + ((BatterDto) humanB[i]).getHitAvg());
      }

    }

    // 순위처리(내림차순)
    HumanDto temp;
    for (int i = 0; i < humanB.length - 1; i++) {
      for (int j = i + 1; j < humanB.length; j++) {

        if (humanB[i] != null && humanB[i].getName().equals("") && humanB[j] != null && humanB[j].getName().equals("")) {
          BatterDto b1 = (BatterDto) humanB[i];
          BatterDto b2 = (BatterDto) humanB[j];
          if (b1.getHitAvg() < b2.getHitAvg()) { // 비교는 타율로 한다
            temp = humanB[i];
            humanB[i] = humanB[j];
            humanB[j] = temp;
          }
        }
      }
    }

  }

  @Override
  public void ptichSort() {

    HumanDto humanB[] = new HumanDto[10];

    // 투수만으로 (배열)구성
    int count = 0;
    for (int i = 0; i < human.length; i++) {
      HumanDto h = human[i];
      if (h != null && h.getName().equals("") == false) {
        if (h instanceof PitcherDto) {
          humanB[count] = h;
          count++;
        }
      }
    }

    for (int i = 0; i < humanB.length; i++) {
      if (humanB[i] != null) {
        // System.out.println(h.info());
        System.out.println((i + 1) + "위" + " 이름:" + humanB[i].getName() + " 방어율:" + ((PitcherDto) humanB[i]).getDefence());
      }

    }

    // 순위처리(오름차순)
    HumanDto temp;
    for (int i = 0; i < humanB.length - 1; i++) {
      for (int j = i + 1; j < humanB.length; j++) {

        if (humanB[i] != null && humanB[i].getName().equals("") && humanB[j] != null && humanB[j].getName().equals("")) {
          PitcherDto p1 = (PitcherDto) humanB[i];
          PitcherDto p2 = (PitcherDto) humanB[j];
          if (p1.getDefence() > p2.getDefence()) { // 비교는 방어율로 한다
            temp = humanB[j];
            humanB[j] = humanB[i];
            humanB[i] = temp;
          }
        }
      }
    }

  }

  @Override
  public void save() {
    // 몇 개 데이터가 있는지 수를 파악
    int count = 0;
    for (HumanDto h : human) {
      if (h != null && h.getName().equals("")) {
        count++;
      }
    }

    if (count == 0) {
      System.out.println("데이터가 없습니다");
      return;
    }

    // (String)배열 설정
    String saveDatas[] = new String[count];

    // human object -> String
    int c = 0;
    for (HumanDto h : human) {
      if (h != null && !h.getName().equals("")) {
        saveDatas[c] = h.toString();
        c++;

      }

    }

    // 저장
    dataProc.dataSave(saveDatas);

    System.out.println("저장했습니다");
  }

  @Override
  public void load() {
    String datas[] = dataProc.dataLoad();

//    for (String s : datas) {
//      System.out.println(s);

    for (int i = 0; i < datas.length; i++) {
      String data[] = datas[i].split("-");

      if (data[4].equals("투수")) {
        human[i] = new PitcherDto(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]), data[4],
            Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
      } else if (data[4].equals("타자")) {
        human[i] = new BatterDto(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]), data[4],
            Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
      }
    }

    counter = datas.length;

    System.out.println("데이터를 로드하였습니다");
  }

  public int search(String name) {
    int index = -1;
    for (int i = 0; i < human.length; i++) {
      HumanDto dto = human[i];
      if (name.equals(dto.getName())) {
        index = i;
        break;
      }
    }
    return index;
  }

}
