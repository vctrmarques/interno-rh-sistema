-- Autor: Marconi Motta
-- Script de correção dos status da folha de pagamento que estavam executando com erro gramatical. Valor incorreto: BLOQUEDO -> Valor Correto: BLOQUEADO.

UPDATE folha_pagamento SET status = 'BLOQUEADO' WHERE status = 'BLOQUEDO';