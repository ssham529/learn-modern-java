package chap11;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * packageName : chap11
 * fileName    : OptionalMain.java
 * @author     : HSS
 * date        : 2020.12.10
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.10        HSS          최초 생성
 **/
public class OptionalMain {

  public String getCarInsuranceNameNullSafeV1(PersonV1 person) {
    if (person != null) {
      CarV1 car = person.getCar();
      if (car != null) {
        Insurance insurance = car.getInsurance();
        if (insurance != null) {
          return insurance.getName();
        }
      }
    }
    return "Unknown";
  }

  public String getCarInsuranceNameNullSafeV2(PersonV1 person) {
    if (person == null) {
      return "Unknown";
    }
    CarV1 car = person.getCar();
    if (car == null) {
      return "Unknown";
    }
    Insurance insurance = car.getInsurance();
    if (insurance == null) {
      return "Unknown";
    }
    return insurance.getName();
  }

  // 컴파일되지 않음:
  // (1)에서 Optional<Person>에 map(Person::getCar) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
  // 그리고 (2)에서 Optional<Car>에 map(Car::getInsurance) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
  // Insurance::getName은 평범한 문자열을 반환하므로 추가 "flatMap"은 필요가 없음.
  /*public String getCarInsuranceName(Person person) {
    Optional<Person> optPerson = Optional.of(person);
    Optional<String> name = optPerson.map(Person::getCar) // (1)
        .map(Car::getInsurance) // (2)
        .map(Insurance::getName);
    return name.orElse("Unknown");
  }*/

  /**
   * 
   */
  public void p372_1(Insurance insurance, Person person) {
	  
	  Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
	  Optional<String> name = optInsurance.map(Insurance::getName);
	  
	  // 위 코드와 차이점은?
	  Optional<Person> optPerson = Optional.of(person);
	  Optional<Optional<Car>> name2 = optPerson.map(Person::getCar);
	  
  }
  
  public String getCarInsuranceName(Optional<Person> person) {
    return person
    	.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }

  /**
   * p378 예제 11-6 사람 목롞을 이용해 보험 회사 이름 찾기
   */
  public Set<String> getCarInsuranceNames(List<Person> persons) {
    return persons.stream()
    	// 사람 목록을 각 사람이보유한 자동차의 Optional<Car> 스트림으로 변환
    	// Stream<Optional<Car>>	
        .map(Person::getCar) 
        
        // FlatMap 연산을 이용해 Optional<Car>을 해당 Optional<Insurance>로 변환
        // Stream<Optional<Insurance>>
        .map(optCar -> optCar.flatMap(Car::getInsurance))  
      
        // Optional<Insuracne>를 해당 이름의 Optional<String>으로 매핑
        // Stream<Optional<String>>
        .map(optInsurance -> optInsurance.map(Insurance::getName))
        
        // Stream<Optional<String>>을 현재 이름을 포함하는 Stream<String>으로 변환
        // Stream<Object>
        .flatMap(Optional::stream)
        
        // 결과 문자열을 중복되지 않은 값을 갖도록 집합으로 수집
        .collect(toSet());
  }
  
  public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
    if (person.isPresent() && car.isPresent()) {
      return Optional.of(findCheapestInsurance(person.get(), car.get()));
    } else {
      return Optional.empty();
    }
  }

  public Insurance findCheapestInsurance(Person person, Car car) {
    // 다른 보험사에서 제공한 질의 서비스
    // 모든 데이터 비교
    Insurance cheapestCompany = new Insurance();
    return cheapestCompany;
  }

  public Optional<Insurance> nullSafeFindCheapestInsuranceQuiz(Optional<Person> person, Optional<Car> car) {
    return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
  }

  public String getCarInsuranceName(Optional<Person> person, int minAge) {
    return person.filter(p -> p.getAge() >= minAge)
     .flatMap(Person::getCar)
     .flatMap(Car::getInsurance)
     .map(Insurance::getName)
     .orElse("Unknown");
  }

}
