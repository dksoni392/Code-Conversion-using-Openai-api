import ast
import os
from openai import OpenAI

def process_java_file(input_file):
    # Open the input file in read mode ('r')
    with open(input_file, 'r') as file:
        content = file.read()

    stack = []  # Stack to track curly braces
    start = 0    # Start index for the current block
    previous = 0  # Previous index for writing to a new file

    block_count = 0  # Counter for creating unique output file names

    # Iterate through each character in the content
    for index, char in enumerate(content):
        if char == '{':
            stack.append(index)  # Push opening bracket onto the stack
        elif char == '}':
            if not stack:
                print("Error: Unmatched closing bracket at index", index)
                return

            stack.pop()
            if len(stack) == 1:
                previous = index + 1

            if len(stack) == 1:
                if index - start > 9000:
                    # Write to a new file
                    output_file = f"split_{block_count}.java"
                    with open(output_file, 'w') as output:
                        output.write(content[start:previous])
                    block_count += 1  # Increment block count
                    start = previous  # Update the start index

    # Write the remaining content to the last output file
    output_file = f"split_{block_count}.java"
    with open(output_file, 'w') as output:
        output.write(content[start:])

# Replace 'javafile.java' with the actual path and name of your Java file
input_file = "javafile.java" # javafile.java have the code to convert 
process_java_file(input_file)# this is function call which split the large code file

client = OpenAI()
file_number = 0

while True:
    file_name = f"split_{file_number}.java"

    if os.path.exists(file_name):
        print( f" file number {file_number} is requesting to convert... ")
        with open(file_name, 'r') as file:
            prompt_text = file.read()
        prompt_systemtext="""you are a programmer who converts Java code into equivalent Python code.
                            you generate the code in Python so that multiple inheritance is used for super-classes and implemented interfaces"""
        while True:
            try:
                
                completion = client.chat.completions.create(
                    model="gpt-3.5-turbo",
                    messages=[
                        {"role": "system", "content":prompt_systemtext },
                        {"role": "user", "content": prompt_text}
                    ]
                )

                generated_python_code = completion.choices[0].message.content

                # Try parsing the Python code string
                formatted_python_code = ast.parse(generated_python_code).body

                for node in formatted_python_code:
                    if isinstance(node, ast.FunctionDef) and node.name == 'main':
                        node.decorator_list.append(ast.Name(id='staticmethod', ctx=ast.Load()))

                print(ast.unparse(formatted_python_code))

                with open('Output.py', 'a') as output_file:
                    if not os.stat('Output.py').st_size == 0:
                        output_file.write('\n')
                    
                    output_file.write(ast.unparse(formatted_python_code))
                    print("Is this code converted correctly? 1.YES\n2.No")
                    x = input()
                    if x == '2':
                        s=input("Write the promte to resolve the error")
                        prompt_text += f"\n{s}\n"
                        continue
                    else:
                        break
            except SyntaxError as e:
                print(f"Syntax error in generated Python code: {e}")
                print("Attempting to continue...")
                break
        file_number += 1
    else:
        print(f"No more files. Exiting the loop.")
        break
