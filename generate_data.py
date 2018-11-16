# -*- coding: utf-8 -*-

import csv
from random import randint

AMOUNT_OF_RECORDS = 100
RESULT_FILENAME = "workers.csv"


class WorkerApi:

    @staticmethod
    def get_month() -> int:
        """Get month from 1 to 12.

        :return:
        """
        return randint(1, 12)

    @staticmethod
    def get_salary() -> int:
        """Return salary.

        :return:
        """
        return randint(1, 1000000)

    @staticmethod
    def rand_int_str(length_of_number:int):
        return ''.join(str(randint(0, 9)) for _ in range(length_of_number))

    @staticmethod
    def get_passport_number():
        """Return passport number  XX XX YYYYYY.

        :return:
        """
        func = WorkerApi.rand_int_str
        return f'{func(2)} {func(2)} {func(6)}'


def write_data():
    """Write needed data from zip to output folder."""
    with open(RESULT_FILENAME, mode='w') as employee_file:
        workers = csv.writer(employee_file, delimiter=',',
                             quotechar='"', quoting=csv.QUOTE_MINIMAL)
        for _ in range(AMOUNT_OF_RECORDS):
            passport_number = WorkerApi.get_passport_number()
            for _ in range(randint(1, 10)):
                workers.writerow([passport_number,
                                  WorkerApi.get_month(),
                                  WorkerApi.get_salary()])


if __name__ == "__main__":
    write_data()
