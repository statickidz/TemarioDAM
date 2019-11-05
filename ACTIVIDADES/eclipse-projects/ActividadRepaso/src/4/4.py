# -*- coding: utf-8 -*- 
'''
4. Escribe un programa que lea valores del usuario hasta que teclee un
numero par, utilizando un bucle while.

'''

while True:
    entrada = raw_input("Escribe un número: ")
    try:
        if int(entrada) % 2 == 0:
            print "Número par, saliendo!"
            break
    except ValueError:
        print "Tienes que escribir números"