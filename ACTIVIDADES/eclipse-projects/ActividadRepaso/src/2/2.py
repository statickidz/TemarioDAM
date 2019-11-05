# -*- coding: utf-8 -*- 
'''
2. Escribe un programa que determine cuál de tres números introducidos
por el usuario es mayor.

'''
numero1 = input("Introduce el número 1/3: ")
numero2 = input("Introduce el número 2/3: ")
numero3 = input("Introduce el número 3/3: ")

numeros = list()
numeros.append(numero1)
numeros.append(numero2)
numeros.append(numero3)

numeros_ordenados = sorted(numeros)
mayor = numeros_ordenados[len(numeros_ordenados)-1]

print "El número mayor de los tres introducidos es el %d" % (mayor)