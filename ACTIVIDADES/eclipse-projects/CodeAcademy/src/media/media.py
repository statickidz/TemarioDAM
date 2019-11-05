# -*- coding: utf-8 -*- 
'''
Created on 20 de ene. de 2016

'''
def media(lista):
    lista_ordenada = sorted(lista)                                                             
    if len(lista_ordenada) % 2 == 0:                                                        
        n = len(lista_ordenada)                                                                           
        mediana = (lista_ordenada[n/2-1] + lista_ordenada[n/2] )/2                                                     
    else:                                                  
        mediana = lista_ordenada[len(lista_ordenada)/2]
        
    return mediana 


print media([7,3,1,4]) # 3.5