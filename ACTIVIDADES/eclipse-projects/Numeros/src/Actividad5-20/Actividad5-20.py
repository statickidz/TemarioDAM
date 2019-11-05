# -*- coding: utf-8 -*- 
'''
Created on 27 de ene. de 2016

'''

print "    Funciones a listas    "
#    map aplica una función a cada elemento 
INum1 = [1, 2, 3]
def cuadrado(n): 
    return n * n
print INum1, "map:", map(cuadrado, INum1)

#    filter verifica si cumplen condición 
INum1 =[1, 2, 3, 4, 5, 6, 7, 8]
def par(n):
    return (n % 2 == 0) 
print INum1, "filter:", filter(par, INum1)

#    reduce opera dos a dos de la lista
INum1 =[1, 2, 3]
def suma(n, m): 
    return n + m 
print INum1, "reduce:", reduce(suma, INum1)
