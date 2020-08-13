# spring-rest-redis-eureka

Requerements:
JDK 9
Maven 3.6.3
Redis 6.0.6 (Port 6379)



BUILD:
cd eureka-service/
mvn clean package

cd one-service/
mvn clean package

cd two-service/
mvn clean package




RUN:
cd ..
 java -jar eureka-service/target/eureka-service.jar
cd ..
java -jar one-service/target/redis-service.jar
cd ..
java -jar two-service/target/redis-service.jar
 
 
 
TEST: (Postman)
Method GET
http://localhost:2222/v1/service1/



RESPONSE:
{
    "id": "a/0302qUDoXc+6R3Z/ZGRuoRQAggf8aAmdVpxu7FEjc9V+CCDUEF5D4IYs6rH1519oxDjtif4gJrednXFUiwiBn2nsLvbV8VPdiwBJXSkFN+6cAO1YIYF/wDyeK/kSt5spjbB04x4GrnrcSUrYgf3oIyp639l6/Q1tVWaRitIlYavNWczSrTgG42Z1iUA3mKIP4am8KY2NZU23iyBhyLehoKyEXPrHlt/B/wqcZkM8uaGHN6JKw+kHfKG94r7tFsT9jDEwu2bR7W3MNcsJgPq6kdPm3a+r/cosDb4vdTISEw/+VCOHrIikjzUsf+mWmQyg1Ff2kVgOLk3xWHoIa281AHohb9v/lkj+pwrZz6qL42QdH8YegAitMO84Lqq5bFPp9JkXguemf7qRV9XDVHqv0+Rr8tHPyugk+9SfUD1jXi3uIUzW0s6NXqd15i3PQNecSOnntmFfrQfZzDxygrcFZj1nHnHZEB5QpR1C44wQ6KBvZxtGupU9XkgxQ3kjAhqp+PN7nJEfqPrbt8RM09PDwccg/pDj59EwiNv7SvfUIUNPTBTIaA7dziRFksDbMEt2KQ3HrioYMNRB+hp1eobvreNuCU2IVPvZIJC+F8XL7XElU73mwf37ltNCQHYvgATunX4jUedUnATRTsm9thzjphsaiZXCGqujfcmMiRk5Mtl1LdOEDhZue5mIvGeiCBMdykZ/vmjUCHNcW5RO9lOiTtLY6owbiRYy4frt6tZK5ABMFUtz4B1kuWvrRly/MKLMJ4dGEb8iZINv3yJ1Wyp9xuqwj/pIijJwwjL6qYnOnpUHNi8EwsVNLlztEs5so1vXAF4ZQDtw0S0WmPmSpcAxwhnZ/5TdN29gk5cek2wgiaJWUrnDSjRpOl49IvdGTd8MGxFr9JJvfmhtmG9LDxPc5cNHiVd4afAb1CEPM3CcF45zXrJ3WNa0EwZtVu8DVTguNQmVr/TcUJ7/V0FssJkdwpe29Q3HC71rCqSM
    
    "value": "MEUCIAYdL8nIOQzKhi3y5eeoX8zkCSY0Z5pRDW2AP6u+Ob9xAiEAltx81EB6MgHV+kIca9cWfJkkYSmikf978y/rYS4oKuU=",
    "publicKey": "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE3iKVBvhnpNet8E6hu5aEKMbFNbWQNR8MA/JL5SxVxnbJuALc8oBMgBej78NPEu5a/7yzV11Ayc5bK320s4VPvA==",
    "algorithm": "SHA256withECDSA and result for sign correct :TRUE"
}
