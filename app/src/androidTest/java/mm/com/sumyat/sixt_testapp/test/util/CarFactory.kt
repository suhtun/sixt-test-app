package mm.com.sumyat.sixt_testapp.test.util

import mm.com.sumyat.sixt_testapp.network.model.Car

object CarFactory {

    fun makeCarList(count: Int): List<Car> {
        val cars = mutableListOf<Car>()
        repeat(count) {
            cars.add(makeCarModel())
        }
        return cars
    }

    fun makeCarModel(): Car {
        return Car(
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomUuid(),
            DataFactory.randomDouble(),
            DataFactory.randomUuid(),
            "aa1",
            DataFactory.randomDouble(),
            DataFactory.randomDouble(),
            DataFactory.randomUuid(),
            "https://cdn.sixt.io/codingtask/images/mini.png"
        )
    }
}