# -*- coding: utf-8 -*- 
'''
Created on 20 de ene. de 2016

'''

#Definición
tVal1=("Valor1",2,2+4j)
print "Tupla:", tVal1
print "Tupla valor con índice 2 d[2]:", tVal1[2]

#No Es dinámico
#Se puede conseguir partes
print "Desde el final índice negativo", tVal1[-2] # -1 es el último 
print "Una parte", tVal1[1:]
print "Una parte", tVal1 [1:2] # inicio incluido índice fin excluido

#Operadores +,+=,*
print "Dos tuplas:", tVal1 + tVal1 
print "Una tupla tres veces:", tVal1 * 3

#Funciones básicas
print"    Funciones básicas    "
print tVal1
print len(tVal1)
print tVal1.index(2 + 4j)
print 3 in tVal1 # ¿Valor 3 en la tupla?

