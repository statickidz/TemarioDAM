# -*- coding: utf-8 -*- 
'''
1. Escribe un programa que pida un número (input) y determine si es par o 
impar, mostrando el mensaje correspondiente

'''

numero = raw_input("Escribe un número: ")
try:
    if int(numero) % 2 == 0:
        print "El número %d es par" % (numero)
    else:
        print "El número %d es impar" % (numero)
except ValueError:
    print "Tienes que escribir un número"