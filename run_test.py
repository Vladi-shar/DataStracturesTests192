#!/usr/bin/env python


import os
import shutil
import sys


def clear_dir(test_project):
    for the_file in os.listdir(test_project):
        file_path = os.path.join(test_project, the_file)
        try:
            if os.path.isfile(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path)
        except Exception as e:
            print(e)


def replace_size_with_getSize():
    os.rename("FloorsArrayList.java", "FloorsArrayListOriginal.java")
    with open("FloorsArrayListOriginal.java") as f:
        new_text = f.read().replace('size()', 'getSize()')
    with open("FloorsArrayList.java" , "w") as f:
        f.write(new_text)



def transfer_student_files(test_project):
    curr_path = os.getcwd()
    replace_size_with_getSize()
    print("Copying student files into test project...")
    for file in os.listdir(curr_path):
        if file == "DynamicSet.java" or file == "FloorsArrayLink.java" or file == "FloorsArrayList.java":
            shutil.copyfile(curr_path + "/" + file, test_project + "/" + file)
    print("Finished copying files.")


def run_mvn_tests(tester, test_num, single_test):
    curr_path = os.getcwd()
    # print(curr_path)
    os.chdir(tester)
    # print("Running tests...")
    if "Correctness" in tester:
        if single_test:
            test_name = get_test_name(test_num)
            print("Running single correctness test: " + test_name + "...")
            os.system("./mvnw -Dtest=" + test_name + " test > " + curr_path + "/correctness_" + test_name +"_testlog.txt")
        else:
            print("Running Correctness tests...")
            os.system("./mvnw test > " + curr_path + "/correctness_testlog.txt")
    elif "Runtime" in tester:
        print("Running Runtime test...")
        os.system("./mvnw test > " + curr_path + "/runtime_testlog.txt")
    os.chdir(curr_path)
    print("Finished running tests.")
    print()


def print_aux_result():
    passed = False
    filepath = os.getcwd() + '/correctness_testlog.txt'
    with open(filepath) as fp:
        line = fp.readline()
        while line:
            if '[INFO] Results:' in line:
                passed = True
                line = fp.readline()
            if "Tests run: 357" in line and passed:
                passed = False
                line = fp.readline()
            if passed:
                if "[ERROR]" in line:
                    print(line)
            line = fp.readline()
    # todo elif runtime message


def print_aux_result_runtime():
    passed = False
    filepath = os.getcwd() + '/runtime_testlog.txt'
    with open(filepath) as fp:
        line = fp.readline()
        while line:
            if "==RunTimeTest=====================================" in line:
                passed = True
                line = fp.readline()
            if "==Done============================================" in line:
                passed = False
                line = fp.readline()
            if passed:
                print(line)
            line = fp.readline()


def get_test_name(test_num):
    print("test num: " + test_num)
    switcher = {
	    '0': "LinkConstructorTest",
        '1': "LinkSetterGetterTest",
        '2': "ListConstructorTest",
        '3': "ListLookupTest",
        '4': "ListInsertTest",
        '5': "ListRemoveTest",
        '6': "ListSortedTest",
        '7': "ListMinimumMaximumTest",
        '8': "ListSuccessorPredecessorTest",
        '9': "ListArraySizesInvariantTest",
    }
    ret = switcher.get(test_num)
    return ret


def main():
    tester = sys.argv[1]
    test_project = tester + "/src/main/java"
    clear_dir(test_project)
    transfer_student_files(test_project)
    if len(sys.argv) > 2:
        if sys.argv[2] == "ordered":
            # print ("Running tests in order")
            for x in range(0, 10):
                run_mvn_tests(tester, str(x), True)
        else:
            # print("single test")
            run_mvn_tests(tester, sys.argv[2], True)
    else:
        run_mvn_tests(tester, 0, False)
        if "Correctness" in test_project:
            print_aux_result()
        elif "Runtime" in test_project:
            print_aux_result_runtime()


if __name__ == '__main__':
    main()
