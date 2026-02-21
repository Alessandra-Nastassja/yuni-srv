package com.nast.yuni.service;

import com.nast.yuni.domain.*;
import com.nast.yuni.repository.*;
import com.nast.yuni.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AtivosCompletoService {

    private final AtivoCompletoRepository ativoRepository;
    private final TesouroDiretoRepository tesouroDiretoRepository;
    private final RendaFixaRepository rendaFixaRepository;
    private final RendaVariavelRepository rendaVariavelRepository;

    public AtivosCompleto criarAtivo(AtivosCompletoRequest request) {
        // Validações gerais
        validarCamposGerais(request);

        AtivosCompleto ativo = AtivosCompleto.builder()
                .nome(request.getNome())
                .tipo(request.getTipo())
                .tipoFonteRenda(request.getTipoFonteRenda())
                .tipoInvestimento(request.getTipoInvestimento())
                .categorizacao(request.getCategorizacaoOutros())
                .risco("indefinido")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        // Se é investimento
        if ("investimentos".equals(request.getTipo())) {
            processarInvestimento(ativo, request);
        } else {
            // Para outros tipos, valorAtual é obrigatório
            if (request.getValorAtual() == null) {
                throw new IllegalArgumentException("valorAtual é obrigatório para tipo: " + request.getTipo());
            }
            ativo.setValorAtual(request.getValorAtual());
        }

        return ativoRepository.save(ativo);
    }

    public List<AtivosCompleto> listarAtivosCompleto() {
        return ativoRepository.findAllByOrderByValorAtualDesc();
    }

    public AtivosCompleto obterAtivoCompletoPorId(Long id) {
        return ativoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ativo completo não encontrado com ID: " + id));
    }

    public void deletarAtivoCompleto(Long id) {
        AtivosCompleto ativo = ativoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ativo completo não encontrado com ID: " + id));
        ativoRepository.delete(ativo);
    }

    private void validarCamposGerais(AtivosCompletoRequest request) {
        // Validação de nome
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (request.getNome().length() > 30) {
            throw new IllegalArgumentException("Nome não pode ter mais de 30 caracteres");
        }

        // Validação de tipo
        List<String> tiposValidos = Arrays.asList(
                "conta_corrente", "meu_negocio", "investimentos",
                "contas_a_receber", "reserva_emergencia", "previdencia_privada", "outros"
        );
        if (request.getTipo() == null || !tiposValidos.contains(request.getTipo())) {
            throw new IllegalArgumentException("Tipo inválido. Valores aceitos: " + tiposValidos);
        }

        // Validação de tipoFonteRenda para conta_corrente e meu_negocio
        if ("conta_corrente".equals(request.getTipo()) || "meu_negocio".equals(request.getTipo())) {
            if (request.getTipoFonteRenda() == null || request.getTipoFonteRenda().isBlank()) {
                throw new IllegalArgumentException("tipoFonteRenda é obrigatório para tipo: " + request.getTipo());
            }
        }
    }

    private void processarInvestimento(AtivosCompleto ativo, AtivosCompletoRequest request) {
        List<String> tiposInvestimentoValidos = Arrays.asList(
                "tesouro_direto", "renda_fixa", "renda_variavel", "outros"
        );

        if (request.getTipoInvestimento() == null || !tiposInvestimentoValidos.contains(request.getTipoInvestimento())) {
            throw new IllegalArgumentException("Tipo de investimento inválido. Valores aceitos: " + tiposInvestimentoValidos);
        }

        switch (request.getTipoInvestimento()) {
            case "tesouro_direto":
                processarTesouroDireto(ativo, request.getTesouroDireto());
                break;
            case "renda_fixa":
                processarRendaFixa(ativo, request.getRendaFixa());
                break;
            case "renda_variavel":
                processarRendaVariavel(ativo, request.getRendaVariavel());
                break;
            case "outros":
                if (request.getRisco() == null || request.getRisco().isBlank()) {
                    throw new IllegalArgumentException("risco é obrigatório para investimentos tipo 'outros'");
                }
                ativo.setRisco(request.getRisco());
                break;
        }
    }

    private void processarTesouroDireto(AtivosCompleto ativo, TesoroDiretoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Dados de tesouro direto são obrigatórios");
        }

        TesouroDireto tesouroDireto = TesouroDireto.builder()
                .tipoTesouro(request.getTipoTesouro())
                .valorInvestido(request.getValorInvestido())
                .valorAtual(request.getValorAtual())
                .dataCompra(request.getDataCompra())
                .dataVencimento(request.getDataVencimento())
                .corretora(request.getCorretora())
                .taxaRentabilidade(request.getTaxaRentabilidade())
                .risco("baixo") // Sempre baixo para tesouro direto
                .build();

        TesouroDireto saved = tesouroDiretoRepository.save(tesouroDireto);
        ativo.setTesouroDireto(saved);
        ativo.setValorAtual(request.getValorAtual());
        ativo.setRisco("baixo");
    }

    private void processarRendaFixa(AtivosCompleto ativo, RendaFixaRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Dados de renda fixa são obrigatórios");
        }

        // Validar tipoAtivoRendaFixa
        List<String> tiposValidosRF = Arrays.asList(
                "cdb", "lc", "debenture", "lci", "lca", "cri", "cra", "outros"
        );
        if (!tiposValidosRF.contains(request.getTipoAtivoRendaFixa())) {
            throw new IllegalArgumentException("Tipo de ativo de renda fixa inválido");
        }

        // Validar tipoTaxa
        List<String> tiposTaxaValidos = Arrays.asList("prefixado", "pos_fixado_cdi", "ipca");
        if (!tiposTaxaValidos.contains(request.getTipoTaxa())) {
            throw new IllegalArgumentException("Tipo de taxa inválido");
        }

        // Validar campos obrigatórios conforme tipoTaxa
        validarCamposPorTipoTaxa(request);

        // Calcular isento
        boolean isento = calcularIsento(request);

        // Calcular valorFinalEstimado
        Double valorFinalEstimado = calcularValorFinalEstimado(request, isento);

        RendaFixa rendaFixa = RendaFixa.builder()
                .tipoAtivoRendaFixa(request.getTipoAtivoRendaFixa())
                .valorInvestido(request.getValorInvestido())
                .valorAtual(request.getValorAtual())
                .corretora(request.getCorretora())
                .dataCompra(request.getDataCompra())
                .dataVencimento(request.getDataVencimento())
                .tipoTaxa(request.getTipoTaxa())
                .taxaContratada(request.getTaxaContratada())
                .percentualCdi(request.getPercentualCdi())
                .cdiAtual(request.getCdiAtual()) // Será preenchido por sistema externo
                .ipcaTaxa(request.getIpcaTaxa())
                .tipoDebenture(request.getTipoDebenture())
                .categoriaRiscoRendaFixa(request.getCategoriaRiscoRendaFixa())
                .isento(isento)
                .irEstimado(request.getIrEstimado())
                .valorFinalEstimado(valorFinalEstimado)
                .build();

        RendaFixa saved = rendaFixaRepository.save(rendaFixa);
        ativo.setRendaFixa(saved);
        ativo.setValorAtual(request.getValorAtual());
        ativo.setRisco(request.getCategoriaRiscoRendaFixa());
    }

    private void validarCamposPorTipoTaxa(RendaFixaRequest request) {
        switch (request.getTipoTaxa()) {
            case "prefixado":
                if (request.getTaxaContratada() == null) {
                    throw new IllegalArgumentException("taxaContratada é obrigatória para tipo de taxa 'prefixado'");
                }
                break;
            case "pos_fixado_cdi":
                if (request.getPercentualCdi() == null) {
                    throw new IllegalArgumentException("percentualCdi é obrigatório para tipo de taxa 'pos_fixado_cdi'");
                }
                if (request.getCdiAtual() == null) {
                    throw new IllegalArgumentException("cdiAtual é obrigatório para tipo de taxa 'pos_fixado_cdi'");
                }
                break;
            case "ipca":
                if (request.getIpcaTaxa() == null) {
                    throw new IllegalArgumentException("ipcaTaxa é obrigatória para tipo de taxa 'ipca'");
                }
                break;
        }
    }

    private boolean calcularIsento(RendaFixaRequest request) {
        List<String> ativosIsentos = Arrays.asList("lci", "lca", "cri", "cra");

        if (ativosIsentos.contains(request.getTipoAtivoRendaFixa())) {
            return true;
        }

        if ("debenture".equals(request.getTipoAtivoRendaFixa()) &&
            "incentivada".equals(request.getTipoDebenture())) {
            return true;
        }

        return false;
    }

    private Double calcularValorFinalEstimado(RendaFixaRequest request, boolean isento) {
        // Calcular rentabilidade estimada
        double rentabilidade = request.getValorAtual() - request.getValorInvestido();

        // Se não for isento, descontar IR
        if (!isento) {
            double irADescontar = request.getIrEstimado() != null ? request.getIrEstimado() : 0;
            return request.getValorAtual() - irADescontar;
        }

        return request.getValorAtual();
    }

    private void processarRendaVariavel(AtivosCompleto ativo, RendaVariavelRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Dados de renda variável são obrigatórios");
        }

        List<String> tiposRendaVariavelValidos = Arrays.asList("acoes", "fii", "etf", "outros");
        if (!tiposRendaVariavelValidos.contains(request.getTipoRendaVariavel())) {
            throw new IllegalArgumentException("Tipo de renda variável inválido");
        }

        // Validar campos obrigatórios conforme tipo
        validarCamposPorTipoRendaVariavel(request);

        RendaVariavel rendaVariavel = RendaVariavel.builder()
                .tipoRendaVariavel(request.getTipoRendaVariavel())
                .quantidade(request.getQuantidade())
                .precoMedio(request.getPrecoMedio())
                .valorAtual(request.getValorAtual())
                .corretora(request.getCorretora())
                .categoriaRiscoRendaVariavel(request.getCategoriaRiscoRendaVariavel())
                .dataCompra(request.getDataCompra())
                .build();

        RendaVariavel saved = rendaVariavelRepository.save(rendaVariavel);
        ativo.setRendaVariavel(saved);
        ativo.setValorAtual(request.getValorAtual());
        ativo.setRisco(request.getCategoriaRiscoRendaVariavel());
    }

    private void validarCamposPorTipoRendaVariavel(RendaVariavelRequest request) {
        if ("acoes".equals(request.getTipoRendaVariavel())) {
            if (request.getDataCompra() == null) {
                throw new IllegalArgumentException("dataCompra é obrigatória para ações");
            }
        }
    }
}

