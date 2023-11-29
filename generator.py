from random import randint

n = 32
passed = False

while not passed:
    try:
        choice = int(input("Select test case to generate (1-3): "))
        passed = True
        match choice:
            case 1:
                for i in range(4):
                    for j in range(2*n):
                        print(j)
            case 2:
                for i in range(n*4):
                    print(randint(0, n*4))
            case 3:
                sequence = []
                for i in range(4):
                    for j in range(n-1):
                        sequence.append(j)
                    for j in range(1, 2*n):
                        sequence.append(j)
                for ele in sequence:
                    print(ele)
            case _:
                passed = False
                print("Please input a proper choice.")
    except:
        print("Please input a proper choice.")
