# -*- coding: utf-8 -*- 
'''
Created on 20 de ene. de 2016

'''
def eliminar_repetidos(lista):
    lista_bypass = list()
    for n in lista:
        if n not in lista_bypass:
            lista_bypass.append(n)
            
    return lista_bypass

print eliminar_repetidos([4, 5, 5, 4])