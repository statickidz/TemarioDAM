# -*- coding: utf-8 -*- 
'''
3. Escribe un programa que permita imprimir los números del 1 al 100 y
calcular la suma de todos los números pares por un lado, y por otro, la
de los impares.

'''

pares = list()
impares = list()

for n in range(1, 101):
    if n % 2 == 0:
        pares.append(n)
    else:
        impares.append(n)

print pares
print impares

print "La suma de los números pares es: %d" % (sum(pares))
print "La suma de los números impares es: %d" % (sum(impares))
