# -*- coding: utf-8 -*- 
'''
5. Escribe un programa que lea una cadena de texto del usuario y para
cada letra indique si es una vocal o una consonante

'''

entrada = raw_input("Escribe una cadena de texto: ")
entrada_split = list(entrada)
vocales = ["a", "e", "i", "o", "u"]

for c in entrada_split:
    if c.lower() in vocales:
        print "La letra %s es una vocal" % c
    elif c == " ":
        print "Espacio"
    else:
        print "La letra %s es una consonante" % c
