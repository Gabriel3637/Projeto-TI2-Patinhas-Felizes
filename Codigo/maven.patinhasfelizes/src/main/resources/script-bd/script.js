const apiUrl1 = "http://localhost:6789/formularios"

const LCapiUrl = 'http://localhost:6789/comentarios';

const apiUrl6 = 'http://localhost:6789/usuarios';

const apiUrl7 = 'http://localhost:6789/reconhecer';

const apiUrl8 = 'http://localhost:6789/enviaranimal'

const apiUrl5 = 'http://localhost:6789/ongs';

const apiUrlJP = 'http://localhost:6789/animais';

const apiUrlCad = 'http://localhost:6789/usuarios';

const apiUrllogin = 'http://localhost:6789/login';

const apiUrlloginong = 'http://localhost:6789/loginong'

// ------------------------------------------------------------ Gabriel Silva -------------------------------------------------------------------

// Set para rastrear IDs únicos
const generatedIds = new Set();



function generateUniqueRandomId(min, max) {
    let id;
    // Gera um novo ID até que ele seja único
    do {
        id = Math.floor(Math.random() * (max - min + 1)) + min;
    } while (generatedIds.has(id));

    // Adiciona o novo ID ao conjunto de IDs gerados
    generatedIds.add(id);
    return id;
}


async function createForm(form) {
    // Tentar fazer a chamada
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(apiUrl1, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(form),
        });
        // Mostrar resultado
        const result = await response.json();
        console.log(result);
    }
    // Chamada de erro
    catch (error) {
        console.error("Error:", error);
    }
}

async function Nome() {
    // Definir dados locais
    let strContent = '';
    let novoForm = '';

    strContent = document.getElementById('nome').value;
    document.getElementById('nome').value = '';
    // Testar se há algo a ser publicado
    if (strContent) {
        // Criar objeto novoForm
        novoForm = {
            "nome": strContent
        };
        // Salvar dados no JSON Server
        createForm(novoForm);
        // Atualizar os dados na tela
        mostrarDados();
    }
}


let perguntas = document.getElementsByClassName("pergunta");
let indicePerguntaAtual = 0;
let campos = [];


async function perguntaFinal() {
    // definir dados da url
    let urlParams = new URLSearchParams(window.location.search);
    let animalId = urlParams.get('id');
    let objAnimal = await readAnimalJP(animalId);
    // buscar animal especifico
    let uniqueId = generateUniqueRandomId(1, 100);
    let novoForm = {
        "id": uniqueId,
        "animal": objAnimal.nome,
        "aondefica": campos[3],
        "nome": campos[0],
        "sexo": campos[5],
        "idade": campos[4],
        "cidade": campos[6],
        "telefone": campos[2],
        "email": campos[1],
        "mora": campos[7],
        "cientes": campos[9],
        "apliberado": campos[8],
        "teveanimal": campos[10],
        "animalsozinho": campos[11],
        "permisao": campos[12],
        "imagem": objAnimal.imagem
    };
    console.log(campos[12]);
    console.log(novoForm);
    createForm(novoForm);
    window.location.href = './index.html';
}

function mostrarProximaPergunta() {
    perguntas[indicePerguntaAtual].style.display = "none"; // Oculta a pergunta atual

    let id = 'pergunta' + (indicePerguntaAtual + 1);
    let campoInput = document.querySelector(`#${id} .resposta`);
    //let valor = campoInput ? campoInput.value.trim() : null; // Obtém o valor do campo e remove espaços em branco
    let valor = campoInput.value;

    if (valor !== "") {
        // Verifica se o valor não está vazio
        campos[indicePerguntaAtual] = valor;
        // Armazena o valor no array campos
        indicePerguntaAtual++;
        // Avança para a próxima pergunta
        if (indicePerguntaAtual < perguntas.length) {
            perguntas[indicePerguntaAtual].style.display = "block"; // Exibe a próxima pergunta
        }
        else {
            perguntaFinal();
        }
    }
    else {
        //GV_alertexcluido alert("Por favor, preencha a resposta antes de prosseguir."); // Alerta se o campo estiver vazio
        perguntas[indicePerguntaAtual].style.display = "block";
    }
}


// ----------------------------------------------- Letícia Silva ----------------------------------------------------------------
async function readRequirements() {
    let requirements = {};
    try {
        const response = await fetch(apiUrl1);
        console.log("Response:", response);
        requirements = await response.json();
        console.log("Requirements:", requirements);
    } catch (error) {
        console.error("Error:", error);
        throw new Error("Servidor não encontrado!");
    }
    return requirements;
}

async function imprimeRequerimentos() {
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
    try{
        let tela = document.getElementById('tela');
        let strHtml = '';
        let objRequerimentos = await readRequirements();
        let GV_animaldados;
        console.log(objRequerimentos);
        strHtml += '<div class="row GV_divrowrequerimentos">';
        if (objRequerimentos.length > 0) {
            for (let i = 0; i < objRequerimentos.length; i++) {
                GV_animaldados = await readAnimalJP(objRequerimentos[i].animalid);
                strHtml += `
                        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 Requerimentos" id="card-${i}">

                                <div class="card imagemreq" id="imagem-${i}" style="font-size: 3vw;">
                                    <img class = "img-animal GV_imagemexibiranimais" src="${GV_animaldados.imagem}" alt="img-animal" style="width: 100%;">
                                    <div class="card-text">
                                        <h5 class="req-animal">${GV_animaldados.nome}</h5>
                                    </div>
                                </div>
                                <div class="centerB">
                                    <div class="button">
                                        <button type="button" class="adotou" data-id="${objRequerimentos[i].id}">Adotou</button>
                                        <button type="button" class="nao-adotou" data-id="${objRequerimentos[i].id}">Não Adotou</button>
                                    </div>
                                </div>

                        </div>
                    `;
            }

            strHtml += '</div>';
            tela.innerHTML = strHtml;




            // Configura os botões de imagem
            for (let i = 0; i < objRequerimentos.length; i++) {
                document.getElementById(`imagem-${i}`).addEventListener('click', function() {
                    imprimeformulario(objRequerimentos[i]);
                });
            }


            let botoesNaoAdotou = document.querySelectorAll('.nao-adotou');

            botoesNaoAdotou.forEach(async function(botao){
                botao.addEventListener('click', async function(event) {
                    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
                    try{
                        imagemcarregando.classList.remove("GV_esconder");
                        // Obtém o ID do formulário do atributo data-id do botão clicado
                        let formId = event.target.getAttribute('data-id');
                        if (!formId) {
                            throw new Error('ID do formulário não encontrado no botão clicado.');
                        }

                        //GV_alertexcluido alert('Concluido! ');
                        // Busca o formulário pelo ID para obter o nome do animal
                        const formResponse = await fetch(apiUrl1 + '/' + formId);
                        if (!formResponse.ok) {
                            throw new Error('Erro na requisição HTTP para obter formulário: ' + formResponse.status);
                        }
                        const form = await formResponse.json();
                        let GV_animalID = form.animalid;
                        let GV_petadotado = await readAnimalJP(GV_animalID);
                        let id = event.target.getAttribute('data-id');
                        // document.getElementById('card-' + id).remove();
                        GV_petadotado.donoid = null;
                        GV_petadotado.datasaida = null;
                        await atualizarAnimal(GV_petadotado.id, GV_petadotado);

                        //GV_alertexcluido alert('Requerimento descartado! ');
                        // Envia a solicitação de DELETE para o servidor
                        fetch(apiUrl1 + '/' + id, {
                            method: 'DELETE',
                        }).then(response => {
                            if (!response.ok) {
                                throw new Error('Erro na requisição HTTP: ' + response.status);
                            }
                            else {
                                location.reload();
                            }
                        }).catch(error => {
                            console.error('Erro na remoção do servidor:', error);
                        });
                        imagemcarregando.classList.add("GV_esconder");
                    }catch(err){
                        imagemcarregando.classList.add("GV_esconder");
                        console.error(err.stack);
                    }
                });

            });

            document.querySelectorAll('.adotou').forEach(botao => {
                botao.addEventListener('click', async function(event) {
                    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
                    try {
                        imagemcarregando.classList.remove("GV_esconder");
                        // Obtém o ID do formulário do atributo data-id do botão clicado
                        let formId = event.target.getAttribute('data-id');
                        if (!formId) {
                            throw new Error('ID do formulário não encontrado no botão clicado.');
                        }

                        //GV_alertexcluido alert('Concluido! ');
                        // Busca o formulário pelo ID para obter o nome do animal
                        const formResponse = await fetch(apiUrl1 + '/' + formId);
                        if (!formResponse.ok) {
                            throw new Error('Erro na requisição HTTP para obter formulário: ' + formResponse.status);
                        }
                        const form = await formResponse.json();
                        let GV_animalID = form.animalid;
                        let GV_petadotado = await readAnimalJP(GV_animalID);
                        let GV_hoje = new Date();
                        let GV_diaatual = (GV_hoje.getDate()).toString().padStart(2, '0');
                        let GV_messtual = (GV_hoje.getMonth() + 1).toString().padStart(2, '0');
                        let GV_anoatual = GV_hoje.getFullYear();
                        GV_petadotado.donoid = form.usuarioid;
                        GV_petadotado.datasaida = `${GV_anoatual}-${GV_messtual}-${GV_diaatual}`;
                        await atualizarAnimal(GV_petadotado.id, GV_petadotado);
                        /*
                        // Busca o animal pelo nome para obter o ID do animal
                        const animaisResponse = await fetch(apiUrlJP + '?nome=' + animalNome);
                        if (!animaisResponse.ok) {
                            throw new Error('Erro na requisição HTTP para obter animal: ' + animaisResponse.status);
                        }
                        const animais = await animaisResponse.json();
                        if (animais.length === 0) {
                            throw new Error('Animal não encontrado: ' + animalNome);
                        }
                        let animalId = animais[0].id;*/

                        // Envia a solicitação de DELETE para o animal
                        const deleteAnimalResponse = await fetch(apiUrlJP + '/' + GV_animalID, {
                            method: 'DELETE',
                        });
                        if (!deleteAnimalResponse.ok) {
                            throw new Error('Erro na requisição HTTP para deletar animal: ' + deleteAnimalResponse.status);
                        }else{
                            location.reload();
                        }

                        /*// Envia a solicitação de DELETE para o formulário
                        const deleteFormResponse = await fetch(apiUrl1 + '/' + formId, {
                            method: 'DELETE',
                        });
                        if (!deleteFormResponse.ok) {
                            throw new Error('Erro na requisição HTTP para deletar formulário: ' + deleteFormResponse.status);
                        }
                        if (deleteFormResponse.ok && deleteAnimalResponse.ok) {
                            location.reload();
                        }*/
                        imagemcarregando.classList.add("GV_esconder");
                    } catch (error) {
                        imagemcarregando.classList.add("GV_esconder");
                        console.error('Erro:', error);
                    }
                });
            });





        }
        else {
            strHtml += `
                <div class="Nreque">
                <h1>Não há requerimentos!!</h1>
                </div>
            `;
            strHtml += '</div>';
            tela.innerHTML = strHtml;
        }
        imagemcarregando.classList.add("GV_esconder");
    }catch(error){
        imagemcarregando.setAttribute("src", "../imagens/icone_error.png");
        imagemcarregando.style.width = "80px";
    }
}

//imprimeRequerimentos();

function formatarmesdadata(dataparaformatar) {
    let resp;
    let GV_arraydata = dataparaformatar.split("-");
    GV_arraydata[1] = (parseInt(GV_arraydata[1]) - 1).toString();
    resp = GV_arraydata.join("-");
    return resp;
}
function contaranos(datainicial) {

    let hoje = new Date();
    let datafinal = `${hoje.getFullYear()}-${hoje.getMonth()}-${hoje.getDay()}`
    let GV_dateinicial = new Date(formatarmesdadata(datainicial));
    let GV_datefinal = new Date(formatarmesdadata(datafinal));
    let diferenca = GV_datefinal - GV_dateinicial;
    let resp = diferenca / (1000 * 60 * 60 * 24 * 365.25);
    return Math.floor(resp);
}

async function imprimeformulario(contato) {
    let GV_usuariodorequerimento = await HG_readUser(contato.usuarioid);
    console.log(GV_usuariodorequerimento.datanascimento);
    document.getElementById('requerimentosDisplay').style.display = 'block';
    let req = document.getElementById('requerimentosDisplay');
    let formHtml = '';

    // Adiciona botão de fechar
    formHtml += '<button id="closeButton">Fechar</button>';
    formHtml += '<div>';

    // Cria um card para o contato selecionado
    formHtml += `
        <div id="requerimento1">
            <div class="requerimento2">
                <div class="requerimento1-text">
                <h4 class="req-animal">Formulário da pessoa que está interessada em adotar: </h4>
                    <ul>
                        <li class="req">Nome: <p class="preq">${GV_usuariodorequerimento.nome}</p></li>
                        <li class="req">CPF: <p class="preq">${GV_usuariodorequerimento.cpf}</p></li>
                        <li class="req">Idade: <p class="preq">${contaranos(GV_usuariodorequerimento.datanascimento, new Date())} anos</p></li>
                        <li class="req">Telefone: <p class="preq">${GV_usuariodorequerimento.telefone}</p></li>
                        <li class="req">E-mail: <p class="preq">${GV_usuariodorequerimento.email}</p></li>
                        <li class="req">Mora em casa ou apartamento?  <p class="preq">${GV_usuariodorequerimento.moradia}</p></li>
                    </ul>
                </div>
            </div>
        </div>
    `;
    formHtml += '</div>';

    req.innerHTML = formHtml;

    // Adiciona evento de clique ao botão de fechar
    document.getElementById('closeButton').addEventListener('click', function() {
        document.getElementById('requerimentosDisplay').style.display = 'none';
    });
}

// ------------------------------------------- João Pedro --------------------------------------------------------


let controleMatchJP = 0;
let controleSlideMatchJP = 0
let objetoGlobalAnimaisJP = {};
let objetoGlobalUsusariosJP = {};

let seletorGeneroJP = document.querySelector('#generoJP');
let seletorPorteJP = document.querySelector('#tamanhoJP');
let seletorEspecieJP = document.querySelector('#especieJP');

let botaoModalMatchJP = document.querySelector('#botaoModalMatchJP');
let botaoModalMatch2JP = document.querySelector('#botaoModalMatch2JP');
let botaoFecharModalMatchJP = document.querySelector('.fechar-modal-MatchJP');
let botaoFecharModalMatch2JP = document.querySelector('.fechar-modal-Match2JP');
let iconeXMatchJP = document.querySelector('#iconeXJP');
let iconeLikeMatchJP = document.querySelector('#iconeLikeJP');
let slideEsquerdaMatchJP = document.querySelector('#slideEsquerdaJP');
let slideDireitaMatchJP = document.querySelector('#slideDireitaJP');
let voltarMatchResultadoJP = document.querySelector('#voltarMatchJP');
let adotarMatchResultadoJP = document.querySelector('#adotarMatchJP');


const botoesModalJP = document.querySelectorAll('.botaoModalJP');
const botoesFecharModalJP = document.querySelectorAll('.fechar-modalJP');


seletorGeneroJP.addEventListener('change', mostrarDadosJP);
seletorPorteJP.addEventListener('change', mostrarDadosJP);
seletorEspecieJP.addEventListener('change', mostrarDadosJP);

botaoModalMatchJP.addEventListener('click', abrirModalMatchJP);
botaoModalMatchJP.addEventListener('click', carregarModalMatchJP);
botaoModalMatch2JP.addEventListener('click', abrirModalMatch2JP);
botaoFecharModalMatchJP.addEventListener('click', fecharModalMatchJP);
botaoFecharModalMatch2JP.addEventListener('click', fecharModalMatch2JP);
iconeXMatchJP.addEventListener('click', proximoAnimalMatchJP);
iconeLikeMatchJP.addEventListener('click', confirmarMatchJP);
slideEsquerdaMatchJP.addEventListener('click', proximaFotoMatchJP);
slideDireitaMatchJP.addEventListener('click', proximaFotoMatchJP);
voltarMatchResultadoJP.addEventListener('click', fecharModalMatch2JP);

botoesModalJP.forEach(button => button.addEventListener('click', abrirModal2JP));
botoesFecharModalJP.forEach(button => button.addEventListener('click', fecharModalJP));


async function readAnimaisJP(apiUrlXJP) {
    let animais = {};
    try {
        const response = await fetch(apiUrlXJP);
        animais = await response.json();
        console.log("Success:", animais);
    } catch (error) {
        console.error("Error:", error);
    }
    return animais;
}

async function readAnimalJP(id) {
    let animal = {};
    try {
        const response = await fetch(`${apiUrlJP}/${id}`);
        animal = await response.json();
        console.log("Success:", animal);
    } catch (error) {
        console.error("Error:", error);
        throw new Error("Servidor nao encontrado!");
    }
    return animal;
}

async function mostrarDadosJP() {
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
    try{
        console.log(imagemcarregando);
        imagemcarregando.classList.remove("GV_esconder");
        let valorGenero = seletorGeneroJP.value;
        let valorPorte = seletorPorteJP.value;
        let valorEspecie = seletorEspecieJP.value;
        let objDados = {};
        let objDadosU = {};
        let strHTML = '';
        let commentsShowDiv = document.querySelector('#cardsJP');

        let objDadosULocal = LCgetUser();
        const apiUrlUsersJP = `${apiUrl6}/${objDadosULocal.id}`;
        objDadosU = await readAnimaisJP(apiUrlUsersJP);
        objetoGlobalUsusariosJP = objDadosU;

        objDados = await readAnimaisJP(apiUrlJP);
        objetoGlobalAnimaisJP = objDados;

        for (const element of objDados) {
            let id = element.id;
            let url = element.imagem;
            let species = element.especie;
            let name = element.nome;
            let description = element.descricao;
            let size = element.porte;
            let sex = element.sexo;
            let urlSex;// = element.urlSexo;
            if (sex == 'F') {
                urlSex = "../imagens/iconeFeminino.png";
            } else if (sex == 'M') {
                urlSex = "../imagens/iconeMasculino.png";
            }

            let isGeneroMatch = valorGenero === 'T' || sex === valorGenero;
            let isPorteMatch = valorPorte === 'T' || size === valorPorte;
            let isEspecieMatch = valorEspecie === 'T' || species === valorEspecie;

            if (isGeneroMatch && isPorteMatch && isEspecieMatch) {
                strHTML += `<div class="cardJP ${sex}" id="${id}">
                        <img src="${url}" alt="" class="GV_imagemexibiranimais">
                        <div>
                            <h1>${name}</h1>
                            <div class="GV_sexo"><img class="sexoJP" id="${sex}" src="${urlSex}" alt="imagem do sexo"></div>
                            <h2>${description}</h2>

                        </div>
                    </div>`;
            }
        }
        commentsShowDiv.innerHTML = strHTML;

        /*const botoesModalJP = document.querySelectorAll('.botaoModalJP');
        botoesModalJP.forEach(button => button.addEventListener('click', abrirModalJP));*/
        const botoesModalJP = document.querySelectorAll('.cardJP');
        botoesModalJP.forEach(button => button.addEventListener('click', abrirModalJP));
        imagemcarregando.classList.add("GV_esconder");
    }catch(error){
        console.log(error);
        if(imagemcarregando != null){
            imagemcarregando.setAttribute("src", "../imagens/icone_error.png");
            imagemcarregando.style.width = "80px";
        }
    }
}

async function abrirModalJP(event) {

    let objDados2 = {};
    let strHTML2 = '';
    let commentsShowDiv2 = document.querySelector('#modaisJP');

    const botaoClicado = event.currentTarget;
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");

    try{
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        const card = botaoClicado.closest('.cardJP');
        if (!card) {
            console.error("O elemento .card mais próximo não foi encontrado.");
            return;
        }
        const cardId = card.id;

        const apiUrlAnimalJP = `${apiUrlJP}/${cardId}`;

        objDados2 = await readAnimaisJP(apiUrlAnimalJP);

        let id = objDados2.id;
        let url = objDados2.imagem;
        let name = objDados2.nome;
        let sex = objDados2.sexo;
        let age =  contaranos(objDados2.datanascimento, new Date());
        let race = objDados2.raca;
        let vaccination = objDados2.vacinas;
        let castrated = objDados2.castrado;
        let history = objDados2.historia;
        let tags = objDados2.etiquetas;
        let size = objDados2.porte;

        if (size == 'P') {
            size = 'Pequeno';
        } else {
            if (size == 'M') {
                size = 'Médio';
            } else {
                size = 'Grande';
            }
        }

        strHTML2 += `
                <div id = "${id}" class="modalJP ${sex}">
                <div id="cabecalhoModalJP">
                    <h1>Vamos Adotar?</h1>
                    <span class="fechar-modalJP"> × </span>
                </div>
                <div class="corpo_modalJP">
                    <section class="flexPerfilJP">
                        <div>
                            <img id="avatarPrincipalJP" src="${url}" alt="">
                        </div>
                        <div class="informacoesJP">
                            <div id="nomeAutorJP">
                                <p>${name}</p>
                            </div>
                            <p><strong>Idade:</strong> ${age} anos</p>
                            <p><strong>Raça:</strong> ${race}</p>
                            <p><strong>Porte:</strong> ${size}</p>
                            <p><strong>Vacinação:</strong> ${vaccination}</p>
                            <p><strong>Castrado:</strong> ${castrated ? "Sim" : "Não"}</p>
                            <div id="descricaoJP">
                                <h4>História:</h4>
                                <p>${history}</p>
                            </div>
                            <p><strong>Tags: </strong> ${tags.join(', ')} </p>
                            <button class="button-44JP" onclick="adotouJP(event)">Adotar!</button>
                            <button class="button-44JP" onclick="comentariosJP(event)">Comentários!</button>
                        </div>
                    </section>
                </div>
                </div>`;

        commentsShowDiv2.innerHTML = strHTML2;
        const botoesFecharModalJP = document.querySelectorAll('.fechar-modalJP');
        botoesFecharModalJP.forEach(button => button.addEventListener('click', fecharModalJP));
        document.querySelector('.modalJP').classList.add('visivelJP');
        GV_imagemdesitecarregando.classList.add('GV_esconder');
    }catch(error){
        GV_imagemdesitecarregando.classList.add('GV_esconder');
        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }
    }
}

function fecharModalJP() {
    document.querySelector('.modalJP').classList.remove('visivelJP');
}

function abrirModal2JP(event) {

    document.querySelector('.modalJP').classList.add('visivelJP');
    const botaoClicado = event.currentTarget;
    const card = botaoClicado.closest('.cardJP');
    if (!card) {
        console.error("O elemento .card mais próximo não foi encontrado.");
        return;
    }

    const cardId = card.id;
    console.log(cardId)

}

function adotouJP(event) {

    let login = LCverificarLogin();

    if (!login) {
        window.location.href = "./login.html";
    } else {

        const botaoClicado = event.currentTarget;
        const card = botaoClicado.closest('.modalJP');
        if (!card) {
            console.error("O elemento .card mais próximo não foi encontrado.");
            return;
        }
        console.log(card);
        const cardId = card.getAttribute('id');


        window.location.href = `./formulario.html?id=${cardId}`;

    }
}

function adotou2JP(id) {

    window.location.href = `./formulario.html?id=${id}`;

}


function adotouIndexJP(event) {

    let login = LCverificarLogin();

    if (!login) {
        window.location.href = "./login.html";
    } else {

        const botaoClicado = event.currentTarget;
        const card = botaoClicado.closest('.modalJP');
        if (!card) {
            console.error("O elemento .card mais próximo não foi encontrado.");
            return;
        }
        console.log(card);
        const cardId = card.getAttribute('id');


        window.location.href = `./formulario.html?id=${cardId}`;

    }
}

function comentariosJP(event) {
    const botaoClicado = event.currentTarget;
    const card = botaoClicado.closest('.modalJP');
    if (!card) {
        console.error("O elemento .card mais próximo não foi encontrado.");
        return;
    }

    const cardId = card.id;
    window.location.href = `./comentarios.html?id=${encodeURIComponent(cardId)}`;
}

function comentariosIndexJP(event) {
    const botaoClicado = event.currentTarget;
    const card = botaoClicado.closest('.modalJP');
    if (!card) {
        console.error("O elemento .card mais próximo não foi encontrado.");
        return;
    }

    const cardId = card.id;
    window.location.href = `./comentarios.html?id=${encodeURIComponent(cardId)}`;
}


function abrirModalMatchJP() {

    let login = LCverificarLogin();

    if (!login) {
        window.location.href = "./login.html";
    } else {
        document.querySelector('.modalMatchJP').classList.add('visivelJP');
    }
}

function abrirModalMatch2JP(id, imagemPet, sexo, imagemPessoa) {
    document.querySelector('.modalMatch2JP').classList.add('visivelJP');
    document.querySelector('.modalMatch2JP');
    let imgAvatarAnimalMatch = document.querySelector('#avatarPrincipalMatchAnimalJP');
    let imgAvatarUsuarioMatch = document.querySelector('#avatarPrincipalMatchUsuarioJP');
    let modalMatch2 = document.querySelector('.modalMatch2JP');

    imgAvatarAnimalMatch.src = imagemPet;
    imgAvatarUsuarioMatch.src = imagemPessoa;


    if (sexo == 'M') {
        modalMatch2.classList.add('corFundo');
        voltarMatchResultadoJP.classList.add('corFundo');
        adotarMatchResultadoJP.classList.add('corFundo');
    } else {
        modalMatch2.classList.remove('corFundo');
        voltarMatchResultadoJP.classList.remove('corFundo');
        adotarMatchResultadoJP.classList.remove('corFundo');
    }

    let btMatchJP = document.querySelector('#adotarMatchJP');

    btMatchJP.addEventListener('click', function() {
        adotou2JP(id);
    });

}


function fecharModalMatchJP() {
    document.querySelector('.modalMatchJP').classList.remove('visivelJP');
}

function fecharModalMatch2JP() {
    document.querySelector('.modalMatch2JP').classList.remove('visivelJP');
    abrirModalMatchJP();
}

async function carregarModalMatchJP() {

    //let objDados = {};

    let modalMatch = document.querySelector('.modalMatchJP');
    let imgAnimalMatch = document.querySelector('#imgMatchJP');
    let nomeAnimalMatch = document.querySelector('#nomeMatchJP');
    let descricaoAnimalMatch = document.querySelector('#descricaoMatchJP');


    //objDados = await readAnimaisJP(apiUrlJP);

    if (objetoGlobalAnimaisJP[controleMatchJP].sexo == 'M') {
        modalMatch.classList.add('corFundo');
    } else {
        modalMatch.classList.remove('corFundo');
    }

    imgAnimalMatch.src = objetoGlobalAnimaisJP[controleMatchJP].imagem;
    nomeAnimalMatch.innerHTML = objetoGlobalAnimaisJP[controleMatchJP].nome;
    descricaoAnimalMatch.innerHTML = objetoGlobalAnimaisJP[controleMatchJP].descricao;

}

async function proximoAnimalMatchJP() {

    controleSlideMatchJP = 0;

    let modalMatch = document.querySelector('.modalMatchJP');

    const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));

    const animateAndUpdateContent = async () => {
        modalMatch.classList.add('slide-out');

        await sleep(200);

        modalMatch.classList.remove('slide-out');
        modalMatch.classList.add('rotate-in');

        modalMatch.offsetHeight;

        await sleep(200);

        modalMatch.classList.remove('rotate-in');

        controleMatchJP++;

        if (controleMatchJP < objetoGlobalAnimaisJP.length) {
            carregarModalMatchJP();
        } else {
            controleMatchJP = 0;
            carregarModalMatchJP();
        }
    }

    animateAndUpdateContent();

    //let objDadosA = {};

    //objDadosA = await readAnimaisJP(apiUrlJP);

}

async function proximaFotoMatchJP(event) {

    let botaoClicado = event.currentTarget;

    //let objDadosA = {};

    //objDadosA = await readAnimaisJP(apiUrlJP);

    if (objetoGlobalAnimaisJP[controleMatchJP].imagem) {
        if (botaoClicado == slideEsquerdaMatchJP) {
            controleSlideMatchJP--;
            if (controleSlideMatchJP < 0) {
                controleSlideMatchJP = objetoGlobalAnimaisJP[controleMatchJP].imagem.length - 1;
            }
        } else {
            if (botaoClicado == slideDireitaMatchJP) {
                controleSlideMatchJP++;
                if (controleSlideMatchJP >= objetoGlobalAnimaisJP[controleMatchJP].imagem.length) {
                    controleSlideMatchJP = 0;
                }
            }
        }

        let imgAnimalMatch = document.querySelector('#imgMatchJP');
        imgAnimalMatch.src = objetoGlobalAnimaisJP[controleMatchJP].imagem[controleSlideMatchJP];
    }

}

async function confirmarMatchJP() {

    //let objDadosA = {};
    let x = 0;
    let contadorEtiquetasMatch = 0;

    //objDadosA = await readAnimaisJP(apiUrlJP);

    while (x < objetoGlobalUsusariosJP.etiquetas.length) {

        for (let y = 0; y < objetoGlobalAnimaisJP[controleMatchJP].etiquetas.length; y++) {
            if (objetoGlobalAnimaisJP[controleMatchJP].etiquetas[y] == objetoGlobalUsusariosJP.etiquetas[x]) {
                contadorEtiquetasMatch++;
            }
        }
        x++;
    }

    if (contadorEtiquetasMatch >= 3) {
        fecharModalMatchJP();
        abrirModalMatch2JP(objetoGlobalAnimaisJP[controleMatchJP].id, objetoGlobalAnimaisJP[controleMatchJP].imagem, objetoGlobalAnimaisJP[controleMatchJP].sexo, objetoGlobalUsusariosJP.imagem);
    } else {
        proximoAnimalMatchJP();
    }

}



async function mostrarDadosIndexJP() {
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");
    let objDados = {};
    let strHTML = '';
    let commentsShowDiv = document.querySelector('#containerCardsJP');

    try{
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        objDados = await readAnimaisJP(apiUrlJP);

        for (let x = 0; x < 8; x++) {
            let id = objDados[x].id;
            let url = objDados[x].imagem;
            let name = objDados[x].nome;
            let description = objDados[x].descricao;

            strHTML += `<div class="col-md-3 d-flex GV_cardsindex" id="card-${id}">
                        <div class="card w-100">
                            <img src="${url}" class="card-img-top" alt="img-animal">
                            <div class="card-body">
                            <h5 class="card-title">${name}</h5>
                            <p class="card-text">${description}</p>
                            </div>
                        </div>
                        </div>`;
        }

        commentsShowDiv.innerHTML = strHTML;

        // Adiciona os event listeners aos botões "Saiba Mais"
        /*document.querySelectorAll('.botaoModalJP').forEach(button => {
            button.addEventListener('click', abrirModalIndexJP);
        });*/
        document.querySelectorAll('.GV_cardsindex').forEach(button => {
            button.addEventListener('click', abrirModalIndexJP)
        });
        GV_imagemdesitecarregando.classList.add("GV_esconder");
    }catch(error){
        
        if(GV_imagemdesitecarregando != null){
            GV_imagemdesitecarregando.setAttribute("src", "../imagens/icone_error.png");
            GV_imagemdesitecarregando.style.width = "80px";
        }
    }
}


function formatarmesdadata(dataparaformatar){
    let resp;
    let GV_arraydata = dataparaformatar.split("-");
    GV_arraydata[1] = (parseInt(GV_arraydata[1])-1).toString();
    resp = GV_arraydata.join("-");
    return resp;
}
function contaranos(datainicial, datafinal){
    let GV_dateinicial = new Date(formatarmesdadata(datainicial));
    let GV_datefinal = datafinal;
    let diferenca = GV_datefinal - GV_dateinicial;
    let resp =  diferenca / (1000 * 60 * 60 * 24 * 365.25);
    return resp.toFixed(1);
}

async function abrirModalIndexJP(event) {
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");

    try{
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        let objDados2 = {};
        let strHTML2 = '';
        let commentsShowDiv2 = document.querySelector('#modalIndexJP');

        const botaoClicado = event.currentTarget;
        const GV_cardId = botaoClicado.getAttribute('id');
        //const GV_tamcardId = ()-1;
        const cardId = GV_cardId.substring(5, GV_cardId.length);

        const apiUrlAnimalJP = `${apiUrlJP}/${cardId}`;

        objDados2 = await readAnimaisJP(apiUrlAnimalJP);

        let id = objDados2.id;
        let url = objDados2.imagem;
        let name = objDados2.nome;
        let sex = objDados2.sexo;
        let age = contaranos(objDados2.datanascimento, new Date());
        let race = objDados2.raca;
        let vaccination = objDados2.vacinas;
        let castrated = objDados2.castrado;
        let history = objDados2.historia;
        let tags = objDados2.etiquetas;
        let size = objDados2.porte;

        if (size == 'P') {
            size = 'Pequeno';
        } else if (size == 'M') {
            size = 'Médio';
        } else {
            size = 'Grande';
        }

        strHTML2 += `
            <div id="${id}" class="modalJP ${sex}">
                <div id="cabecalhoModalJP">
                    <h1>Vamos Adotar?</h1>
                    <span class="fechar-modalJP">×</span>
                </div>
                <div class="corpo_modalJP">
                    <section class="flexPerfilJP">
                        <div>
                            <img id="avatarPrincipalJP" src="${url}" alt="">
                        </div>
                        <div class="informacoesJP">
                            <div id="nomeAutorJP">
                                <p>${name}</p>
                            </div>
                            <p><strong>Idade:</strong> ${age} anos</p>
                            <p><strong>Raça:</strong> ${race}</p>
                            <p><strong>Porte:</strong> ${size}</p>
                            <p><strong>Vacinação:</strong> ${vaccination}</p>
                            <p><strong>Castrado:</strong> ${castrated ? "Sim" : "Não"}</p>
                            <div id="descricaoJP">
                                <h4>História:</h4>
                                <p>${history}</p>
                            </div>
                            <p><strong>Tags:</strong> ${tags.join(', ')}</p>
                            <button class="button-44JP" onclick="adotouIndexJP(event)">Adotar!</button>
                            <button class="button-44JP" onclick="comentariosIndexJP(event)">Comentários!</button>
                        </div>
                    </section>
                </div>
            </div>`;

        commentsShowDiv2.innerHTML = strHTML2;

        // Adiciona o event listener para fechar o modal
        document.querySelector('.fechar-modalJP').addEventListener('click', fecharModalIndexJP);
        document.querySelector('.modalJP').classList.add('visivelJP');
        GV_imagemdesitecarregando.classList.add('GV_esconder');
    }catch(error){
        GV_imagemdesitecarregando.classList.add('GV_esconder');
        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }
    }
}

function fecharModalIndexJP() {
    document.querySelector('.modalJP').classList.remove('visivelJP');
    document.querySelector('#modaisJP').innerHTML = ''; // Limpa o conteúdo do modal
}

//Ana Clara

/*
Tela de Cadastro
*/

/*
Funcao para criar objeto json
*/
async function AC_botaoCadastrarOnClick() {
    //    obter dados preenchidos
    const cpnjInputValue = document.getElementById("cnpj-input").value;
    const enderecoInputValue = document.getElementById("endereco-input").value;
    const telefoneInputValue = document.getElementById("telefone-input").value;
    const emailInputValue = document.getElementById("email-input").value;
    const nomeInputValue = document.getElementById("nome-input").value;
    const senhaInputValue = document.getElementById("senha-input").value;
    const confirmarSenhaInputValue = document.getElementById("confirmar-senha-input").value;
    const logoInputValue = document.getElementById("input-logo").value;
    const id = 0;
    //    confirmar senha
    if (senhaInputValue != confirmarSenhaInputValue) {
        document.getElementById("confirmar-senha-msg-erro").style.display = "block";
    } else {
        document.getElementById("confirmar-senha-msg-erro").style.display = "none";
        //    criar objeto JSON
        const objetoResultado = {
            id: "0",
            nome: nomeInputValue,
            cnpj: cpnjInputValue,
            endereco: enderecoInputValue,
            telefone: telefoneInputValue,
            email: emailInputValue,
            senha: senhaInputValue,
            logo: logoInputValue
        }
        console.log(objetoResultado);        //    mostra no console o objeto criado 
        AC_updateONG(id, objetoResultado);     //    executa a funcao para lancar para o Json Server
    }
} // end AC_botaoCadastrarOnClick ( )

/*
Lancar objeto JSON para o Json Server
*/
async function AC_updateONG(id, objetoResultado) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrl5}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(objetoResultado),
        });
        //  mostrar resultado
        //alert("Cadatro efetuado com sucesso!");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        //alert("Erro ao efetuar cadastro (JSON Server indisponível).");
        throw new Error("Servidor não encontrado!");
    }
} // end updateONG ( )

/*
Sobre a ONG
*/

/*
Ler Dados do JsonServer
*/
async function AC_obterDados() {
    //  definir dados locais
    let dados = {};
    //  tentar fazer a chamada
    try {
        //  definir a chamade HTTP do JSON Server
        const response = await fetch(apiUrl5);
        dados = await response.json();
        //  mostrar resultado
        console.log("Success:", dados);
        console.log("Sucesso ao ler dados!");
    }
    catch (error) {
        console.error("Error:", error);
        console.log("Erro ao ler dados (JSON Server indisponível).");
        throw new Error("Servidor não encontrado!");
    }
    //  retornar
    return (dados);
} // end AC_obterDados ( )

/*
Função que lê os Dados do JSON e chama as funções dos elementos
*/
async function ACmostrarDados() {
    //  definir dados locais
    let objDados = {};
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");

    try{
        //  chamar funcao para ler dados do JSON Server
        objDados = await AC_obterDados();
        console.log("OBJ:", objDados);

        //testar se existe objeto e executar funcoes
        if (objDados) {
            AC_mostrarCNPJ(objDados);
            AC_mostrarNome(objDados);
            AC_mostrarLogo(objDados);
            AC_mostrarEmail(objDados);
            AC_mostrarEndereco(objDados);
            AC_mostrarHistoria(objDados);
            AC_mostrarTelefone(objDados);
        }
        imagemcarregando.classList.add("GV_esconder");
    }catch(error){
        imagemcarregando.classList.add("GV_esconder");
        LCdisplayMessage("ERRO: Servidor não encontrado!");
    }
//retorne
return objDados;

}// end mostrarDados  ( )

/*
Função de mostrar o CNPJ
*/
async function AC_mostrarCNPJ(dados) {
    console.log("dados no CNPJ:", dados);
    let cnpjHtml = '';
    // Obtém o elemento <span> pelo id
    var elementoCNPJ = document.getElementById("CNPJONG");
    cnpjHtml += `${dados[0].cnpj}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementoCNPJ.innerHTML = cnpjHtml;

}//end mostrarCNPJ

/*
Função que mostra a historia
*/
async function AC_mostrarHistoria(dados) {
    console.log("dados na historia:", dados);
    let HistHtml = '';
    // Obtém o elemento <span> pelo id
    var elementohistoria = document.getElementById("historiaONG");
    HistHtml += `${dados[0].historia}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementohistoria.innerHTML = HistHtml;

}//end mostrarHistoria

/*
Função de mostrar o Nome
*/
async function AC_mostrarNome(dados) {
    console.log("dados no Nome:", dados);
    let NomeHtml = '';
    // Obtém o elemento <span> pelo id
    var elementoNome = document.getElementById("nomeONG");
    NomeHtml += `${dados[0].nome}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementoNome.innerHTML = NomeHtml;

}//end mostrarNome

/*
Função de mostrar o endereco
*/
async function AC_mostrarEndereco(dados) {
    console.log("dados no Endereco:", dados);
    let EnderecoHtml = '';
    // Obtém o elemento <span> pelo id
    var elementoEndereco = document.getElementById("enderecoONG");
    EnderecoHtml += `${dados[0].endereco}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementoEndereco.innerHTML = EnderecoHtml;

}//end mostrarEndereco

/*
Função de mostra o Email
*/
async function AC_mostrarEmail(dados) {
    console.log("dados no Email:", dados);
    let EmailHtml = '';
    // Obtém o elemento <span> pelo id
    var elementoEmail = document.getElementById("emailONG");
    EmailHtml += `${dados[0].email}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementoEmail.innerHTML = EmailHtml;

}//end mostrarEmail

/*
Função de mostra o Telefone
*/
async function AC_mostrarTelefone(dados) {
    console.log("dados no Telefone:", dados);
    let TelefoneHtml = '';
    // Obtém o elemento <span> pelo id
    var elementotelefone = document.getElementById("telefoneONG");
    TelefoneHtml += `${dados[0].telefone}`;

    // Define o conteúdo do elemento <span> com o resultado da função obterNome
    elementotelefone.innerHTML = TelefoneHtml;

}//end mostrarTelefone

/*
Função de mostra a Logo
*/
async function AC_mostrarLogo(dados) {
    console.log("dados na logo:", dados);
    let LogoHtml = dados[0].logo;
    // Obtém o elemento <span> pelo id
    var elementologo = document.getElementById("imagem");
    // Define o atributo src do elemento <img> com o resultado da função obterImagem
    var imagemSrc = LogoHtml
    if (imagemSrc !== "Imagem não encontrada" && imagemSrc !== "Erro ao converter JSON") {
        elementologo.src = imagemSrc;
    } else {
        // Caso a imagem não seja encontrada ou haja erro, remove a imagem do display
        elementologo.style.display = "none";
    }

}//end mostrarLogo


//Henrique 
//const apiUrl6 = 'https://17888d69-e5c6-41a4-a17c-98ca11856607-00-f4ikhqwcowcp.janeway.replit.dev/users';

// Exibe mensagem em um elemento de ID msg
function HG_displayMessage(message) {
    document.getElementById('msg').innerHTML = '<div class="alert alert-warning">' + message + '</div>';
}

async function HG_alterUser(user) {
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrl6}/${user.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });
        // Mostrar resultado
        HG_displayMessage("Sucesso ao editar usuário!");
    }
    // Chamada de erro
    catch (error) {
        console.error("Error:", error);
        HG_displayMessage("Erro ao editar usuário (JSON Server indisponível).");
        throw new Error("Servidor não encontrado!");
    }
} // end HG_alterUser()

async function HG_deleteUser(id) {
    // Tentar fazer a chamada
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrl6}/${id}`, {
            method: 'DELETE',
        });
        // Mostrar resultado
        HG_displayMessage("Sucesso ao excluir usuário!");
    }
    // Chamada de erro
    catch (error) {
        console.error("Error:", error);
        HG_displayMessage("Erro ao excluir usuário (JSON Server indisponível).");
        throw new Error("Servidor não encontrado!");
    }
} // end HG_deleteUser()

async function HG_readUser(id) {
    console.log('entrou');
    let perfil = {}; console.log('id = ' + id);
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrl6}/${id}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        perfil = await response.json();
        // Mostrar resultado
        //console.log("Success:", perfil);
    } catch (error) {
        console.error("Error:", error);
        //HG_displayMessage("Erro ao ler perfil");
        throw new Error("Servidor nao encontrado");
    }
    return perfil;
} // end HG_readUser()

// ------------------------------------------- LUCAS CARNEIRO --------------------------------------------------------

function LCdisplayMessage(mensagem) {
    msg = document.getElementById('LCmsg');
    msg.innerHTML = '<div class="alert alert-warning">' + mensagem + '</div>';
} // end displayMessage ( )

function GVescondermensagem(){
    msg = document.getElementById('LCmsg');
    msg.innerHTML = '';
}

async function LCreadComments(animalId) {
    //  definir dados locais
    let comments = {};
    let resp = [];
    //  tentar fazer a chamada
    try {
        //  definir a chamade HTTP do JSON Server
        const response = await fetch(LCapiUrl);
        comments = await response.json();
        
        for(let comentario of comments){
            console.log(comentario);
            if(comentario.animalid == animalId){
                resp.push(comentario);
            }
        }
        //  mostrar resultado
        //console.log("Success:", comments);
        //  displayMessage("Sucesso ao ler comentario!");
    }
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao ler comentario (JSON Server indisponível).");
    }
    //  retornar
    return (resp);
} // end readComments ( )

async function LCreadComment(commentId) {
    //  definir dados locais
    let comment = {};
    //  tentar fazer a chamada
    try {
        //  definir a chamade HTTP do JSON Server
        const response = await fetch(`${LCapiUrl}/${commentId}`);
        comment = await response.json();
        //  mostrar resultado
        //console.log("Success:", comment);
        //  displayMessage("Sucesso ao ler comentario!");
    }
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao ler comentario (JSON Server indisponível).");
    }
    //  retornar
    return (comment);
} // end readComments ( )

async function LCcreateComment(comment) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(LCapiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(comment),
        });
        //  mostrar resultado
        LCdisplayMessage("Sucesso ao criar comentario!");
        //  atualizar a pagina
        LCmostrarComentarios();
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao criar comentario (JSON Server indisponível).");
    }
} // end createComment ( )

async function LCupdateComment(id, comentario) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(`${LCapiUrl}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(comentario),
        });
        //  mostrar resultado
        LCdisplayMessage("Sucesso ao editar comentario!");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao editar comentario (JSON Server indisponível).");
    }
} // end updateComment ( )

async function LCdeleteComment(id) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(`${LCapiUrl}/${id}`, {
            method: 'DELETE',
        });
        //  mostrar resultado
        LCdisplayMessage("Sucesso ao excluir comentario!");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao excluir comentario (JSON Server indisponível).");
    }
} // end deleteComment ( )

/*
    Manipular a obtenção do usuário
*/

function LCgetUser() {
    //  definir dados locais
    let strUser = localStorage.getItem('db');
    let objUser = {};
    //  testar leitura
    if (strUser) {
        objUser = JSON.parse(strUser);
    }
    //  retornar
    return (objUser);
} // end getUser ( )

/*
    Manipular a exibição de dados
*/

async function LCmostrarComentarios() {
    //  definir dados locais
    const urlParams = new URLSearchParams(window.location.search);
    let strAnimalId = urlParams.get('id');
    let animalId = parseInt(strAnimalId);
    let objComentarios = {}
    let strHTML = ''
    let commentsShowDiv = document.querySelector('#LCcommentsShowDiv');
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");
    
    try{
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        //  chamar funcao para ler dados do JSON Server
        objComentarios = await LCreadComments(animalId);
        //  testar se há comentarios
        if (Object.keys(objComentarios).length === 0) {
            strHTML += `<div class=Lcomment>
                        <p class="LCcommentContent">Ainda não há comentários publicados. Seja o primeiro a comentar!</p>
                        </div>`
            document.querySelector('#LCcommentsContainer').style = 'margin-bottom: 255px';
        }
        else {
            let GV_listadeusuarios = await readUsers();
            //  repetir para cada elemento dos dados
            for (let x = 0; x < objComentarios.length; x++) {
                //  definir dados locais
                let user = GV_listadeusuarios.find(element => element.id == objComentarios[x].usuarioid);
                //  testar o usuario
                if (Object.keys(user).length > 0) {
                    //  definir dados locais
                    let username = user.nome;
                    let content = objComentarios[x].conteudo;
                    let id = objComentarios[x].id;
                    //  adicionar os valores à formatação de string a ser inserida
                    strHTML += `<div class="LCcomment" id=${id}>
                                <div class="LCcontentHolder">
                                    <p class="LCcommentP"><span class="LCcommentUsername">${username}</span>:<span class="LCcommentContent">${content}</span></p>
                                </div>
                                <div class="LCdropdown">
                                    <button class="LCbtnDropdown" id="${id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" wclassth="16" height="16" fill="currentColor" class="bi bi-three-dots-vertical" viewBox="0 0 16 16"><path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/></svg>
                                    </button>
                                    <div class="LCdropdownContent">
                                        <button class="LCbtnHidden LCbtnEditar">Editar</button>
                                        <button class="LCbtnHidden LCbtnExcluir">Excluir</button>
                                    </div>
                                </div>
                            </div>`
                }
            }
        }
        //  inserir string na div dos comentários
        commentsShowDiv.innerHTML = strHTML;
        //  chamar funcao para lidar como botao Dropdown
        LChandleDropdownButton();
        /*
            Impedir a atualização da página com cada envio do formulário
        */
        document.getElementById('LCform').addEventListener('submit', async (evento) => {
            evento.preventDefault();
            await LClerInputComentario();
        })
        GV_imagemdesitecarregando.classList.add("GV_esconder");
    }catch(error){
        GV_imagemdesitecarregando.classList.add('GV_esconder');
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/icone_error.png");
        GV_imagemdesitecarregando.style.width = "80px";

        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }

    }
} // end mostrarComentarios  ( )

/*
    Manipular a leitura do input e a criação de comentário
*/

async function LClerInputComentario() {
    //  definir dados locais
    const urlParams = new URLSearchParams(window.location.search);
    let strAnimalId = urlParams.get('id');
    let animalId = strAnimalId;
    let strContent = '';
    let novoComentario = {};
    let objUser = LCgetUser();
    let login = LCverificarLogin();
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");

    try{
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        //  ler um comentario novo
        strContent = document.getElementById('LCcampoComment').value;
        document.getElementById('LCcampoComment').value = ''
        //  testar se há algo a ser publicado
        if (strContent) {
            // testar se esta logado
            if (!login) {
                window.location.href = "./login.html";
            }
            else {
                //  criar objeto novoComentario
                novoComentario = {
                    usuarioid: objUser.id,
                    animalid: animalId,
                    id: Date.now().toString(),
                    conteudo: strContent
                }; console.log(novoComentario);
                //  salvar dados no JSON Server
                await LCcreateComment(novoComentario);
                //  atualizar os dados na tela
                await LCmostrarComentarios();
            }
        }
        GV_imagemdesitecarregando.classList.add("GV_esconder");
    }catch(error){
        
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/icone_error.png");
        GV_imagemdesitecarregando.style.width = "80px";

        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }

        
    }
} // end lerInput ( )

/*
    Manipular a edição de comentário
*/

async function LCeditarComentario(e) {
    //  definir dados
    let id = e.currentTarget.closest('.LCcomment').id;
    let contentHolder = document.querySelector(`div.LCcomment[id="${id}"] .LCcontentHolder`);
    let strHTML = '';
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");
    //  testar se o usuario pode editar

    try{
        GVescondermensagem();
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/gatocarregando-unscreen.gif");
        GV_imagemdesitecarregando.style.width = "100px";
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        let previousComment = await LCreadComment(id);
        let user = await HG_readUser(previousComment.usuarioid);
        if (user.id != LCgetUser().id) {
            throw new Error("Nao é possivel editar comentario de outro usuario!");
        }
        else {
            //  adicionar caixa de texto para edicao
            strHTML += `<div class="LCcommentEdit" id=${id}>
                        <div style="display: inline-block;">
                            <p class="LCcommentP"><span class="LCcommentUsername">${user.nome}</span>:</p>
                        </div>
                        <div>
                            <div class="LCformEdit">
                                <input type="text" placeholder="Digite o seu novo comentário..." name="comment" value="${previousComment.conteudo}" class="LCcampoUpdateComment">
                                <button type="submit" class="LCbtnEdit">OK</button>
                                <button type="submit" class="LCbtnCancelEdit">Cancelar</button>
                            </div>   
                        </div>
                    </div>`;
            //  mostrar caixa de texto na tela
            contentHolder.innerHTML = strHTML;
            //  controlar o botao de confirmacao
            let btnEdit = document.querySelector('.LCbtnEdit');
            btnEdit.addEventListener('click', async () => {
                //  indentificar o campo input
                let inputEdit = document.querySelector('.LCcampoUpdateComment');
                //  resgatar o conteúdo digitado
                let strContent = inputEdit.value;
                //  esvaziar o input
                inputEdit.value = '';
                //  editar o objeto
                previousComment.conteudo = strContent;
                //  editar o JSON Server
                await LCupdateComment(id, previousComment);
                //  mostrar os comentarios novamente
                await LCmostrarComentarios();
            });
            //  controlar o botao de cancelamento
            let btnCancelEdit = document.querySelector('.LCbtnCancelEdit');
            btnCancelEdit.addEventListener('click', async () => {
                //  recarregar a pagina
                await LCmostrarComentarios();
            });
        }
        GV_imagemdesitecarregando.classList.add("GV_esconder");
    }catch(error){
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/icone_error.png");
        GV_imagemdesitecarregando.style.width = "80px";

        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }else if(error.message == 'Nao é possivel editar comentario de outro usuario!'){
            LCdisplayMessage('Não é possível editar o comentário de outro usuário!');
        }
    }
} // end editarComentario ( )

/*
    Manipular a exclusão de comentário
*/

async function LCexcluirComentarios(e) {
    //  definir dados
    let id = e.currentTarget.closest('.LCcomment').id;
    let GV_imagemdesitecarregando = document.getElementById("GV_imagemdesitecarregando");

    try{
        GVescondermensagem();
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/gatocarregando-unscreen.gif");
        GV_imagemdesitecarregando.style.width = "100px";
        GV_imagemdesitecarregando.classList.remove("GV_esconder");
        let comment = await LCreadComment(id);
        let user = LCgetUser();
        console.log(comment);
        console.log(user.id);
        if (comment.usuarioid != user.id) {
            throw new Error('Não é possível excluir o comentário de outro usuário!');
        }
        else {
            //  remover comentario do JSON Server
            await LCdeleteComment(id);
            //  recarregar a pagina
            await LCmostrarComentarios();
        }
        GV_imagemdesitecarregando.classList.add("GV_esconder");
    }catch(error){
        GV_imagemdesitecarregando.setAttribute("src", "../imagens/icone_error.png");
        GV_imagemdesitecarregando.style.width = "80px";
        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        }else if(error.message == 'Não é possível excluir o comentário de outro usuário!'){
            LCdisplayMessage('Não é possível excluir o comentário de outro usuário!');
        }
    }
}

function LCrandomNumber() {
    //  gerar um numero de 1 a 99;
    let random = Math.floor(Math.random() * 1000);
    //  retornar
    return random;
} // end randomNumber ( )

/*
    Funcao para controlar o evento de mostrar cada dropdownButton
*/

function LChandleDropdownButton() {
    //  definir dados locais
    let btnsDropdown = document.getElementsByClassName('LCbtnDropdown');
    // repetir para cada botão
    for (let x = 0; x < btnsDropdown.length; x++) {
        // ação individual para cada botão
        btnsDropdown[x].addEventListener('click', (e) => {
            LCdropdownButton(e.currentTarget);
        })
    }
} // end handleDropdownButton ( )

/*
    Funcao para controlar o Dropdown Button
*/

function LCdropdownButton(individualDropdownButton) {
    // definir dados locais
    let id = individualDropdownButton.id;
    let container = document.querySelector(`div.LCcomment[id="${id}"] .LCdropdownContent`);
    let btnEditar = document.querySelector(`div.LCcomment[id="${id}"] .LCbtnEditar`);
    let btnExcluir = document.querySelector(`div.LCcomment[id="${id}"] .LCbtnExcluir`);
    // testar se os botões estão escondidos
    if (!container.classList.contains('show')) {
        // mostrar
        container.classList.add('show');
        // criar eventos individuais Editar e Excluir
        btnEditar.addEventListener('click', (e) => {
            LCeditarComentario(e);
            container.classList.remove('show');
        });
        btnExcluir.addEventListener('click', (e) => {
            LCexcluirComentarios(e);
            container.classList.remove('show');
        });
    }
    else {
        // esconder
        container.classList.remove('show');
    }
} // end dropdownButton ( )

// ------------------------ login ----------------------



/*
  Controlar o login do usuario
*/

function LCpaginaInicial() {
    window.location.href = "./index.html";
} // end LCpaginaInicial ( )

function LCpaginaInicialONG() {
    window.location.href = "./telainicialONG.html";
} // end LCpaginaInicialONG ( )

function LCcriarUsuarioLS(user) {
    localStorage.setItem('db', JSON.stringify(user));
} // end LCcriarUsuarioLS ( )

function LCexcluirUsuarioLS() {
    localStorage.removeItem('db');
} // end LCexcluirUsuarioLS ( )

function LCverificarLogin() {
    // definir dados locais
    let user = LCgetUser();
    let controle = false;
    // testar se há usuário logado
    if (!(Object.keys(user).length > 0))
        window.location.href = "./login.html";
    else
        controle = true;
    // retornar
    return (controle);
} // end LCverificarLogin( )


function LCverificarPerfil() {
    //  testar login
    if (LCverificarLogin()) {
        console.log('logado');
    }
} // end LCverificarPerfil ( )

async function readUsers() {
    //  definir dados locais
    let users = {};
    //  tentar fazer a chamada
    try {
        //  definir a chamade HTTP do JSON Server
        const response = await fetch(apiUrl6);
        users = await response.json();
        //  mostrar resultado
        console.log("Success:", users);
        //  displayMessage("Sucesso ao ler usuarios!");
    }
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao ler usuarios (JSON Server indisponível).");
    }
    //  retornar
    return (users);
} // end readUsers ( )


//Metodo fetch para verificar o login usuario
/*
async function VerificarRealizarLogin(APIurlverificarusuario, APIurlverificarong, loginusuario) {
    let GV_perfildousuario = null;
    try {
        //  definir a chamada HTTP do JSON Server
        await fetch(APIurlverificarusuario, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(loginusuario),
        }).then(async response => {
            if(!response.ok){
                await fetch(APIurlverificarong, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(loginusuario),
                }).then(response => {
                    if(!response.ok){
                        throw new Error("Network response was not ok");
                    }
                    GV_perfildousuario = response.json();
                });
            } else{
                GV_perfildousuario = response.json();
            }
        });
        console.log(loginusuario);
        HG_displayMessage("Sucesso ao logar com usuário");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao logar com usuário");
        throw new Error("Servidor nao encontrado!");
    }
    return GV_perfildousuario;
}
    */

async function VerificarRealizarLoginUsuario(APIurlverificarusuario, loginusuario) {
    let GV_perfildousuario = null;
    //  definir a chamada HTTP do JSON Server
    await fetch(APIurlverificarusuario, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginusuario),
    }).then(async response => {
        if(response.ok){
            GV_perfildousuario = response.json();
        }
    });
    console.log(loginusuario);

    //Retorno
    return GV_perfildousuario;
}

async function VerificarRealizarLoginOng(APIurlverificarong, loginusuario) {
    let GV_perfildaong = null;
    //  definir a chamada HTTP do JSON Server
    await fetch(APIurlverificarong, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginusuario),
    }).then(async response => {
        if(response.ok){
            GV_perfildaong = response.json();
        }
    });
    console.log(loginusuario);

    //Retorno
    return GV_perfildaong;
}

async function VerificarRealizarLoginUsuarioID(APIurlverificarusuario, loginusuario) {
    let GV_perfildousuario = null;
    //  definir a chamada HTTP do JSON Server
    await fetch(APIurlverificarusuario, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginusuario),
    }).then(async response => {
        if(response.ok){
            GV_perfildousuario = response.json();
        }
    });

    //Retorno
    return GV_perfildousuario;
}

async function VerificarRealizarLoginOngID(APIurlverificarong, loginusuario) {
    let GV_perfildaong = null;
    //  definir a chamada HTTP do JSON Server
    await fetch(APIurlverificarong, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginusuario),
    }).then(async response => {
        if(response.ok){
            GV_perfildaong = response.json();
        }
    });

    //Retorno
    return GV_perfildaong;
}

async function checkUser(event) {
    //  definir dados locais  
    event.preventDefault();  
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
    imagemcarregando.classList.remove("GV_esconder");
    const GV_formulariodeloginusuario = document.getElementById("form-contato");
    let GV_dadosperfillido = null;

    try{
        let GV_formloginusuario = new FormData(GV_formulariodeloginusuario);
        let GV_objloginusuario = Object.fromEntries(GV_formloginusuario);

        console.log(GV_objloginusuario);

        if((GV_dadosperfillido = await VerificarRealizarLoginUsuario(apiUrllogin, GV_objloginusuario)) != null){
            HG_displayMessage("Sucesso ao logar com usuário");
            console.log("Usuario identificado: ", GV_dadosperfillido);
            LCcriarUsuarioLS(GV_dadosperfillido);
            LCdisplayMessage('Usuário válido.');
            clearLogin();
            LCpaginaInicial();
        } else if((GV_dadosperfillido = await VerificarRealizarLoginOng(apiUrlloginong, GV_objloginusuario)) != null){
            HG_displayMessage("Sucesso ao logar como ong");  
            console.log("Ong identificada: ", GV_dadosperfillido);
            LCcriarUsuarioLS(GV_dadosperfillido);
            clearLogin();
            LCpaginaInicialONG();
        } else {
            throw new Error("Usuario nao encontrado!");
        }

        imagemcarregando.classList.add("GV_esconder");
    }catch(error){

        imagemcarregando.classList.add("GV_esconder");

        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if(error.message == 'Usuario nao encontrado!'){

            LCdisplayMessage('ERRO: Email ou senha incorretos.');
            
        }

        console.log(error);
        
    }
} // end checkUser ()

function clearLogin() {
    document.querySelector('#inputEmail').value = '';
    document.querySelector('#inputSenha').value = '';
}

function startLogin() {
    let btnInsert = document.querySelector('#btnInsert');
    btnInsert.addEventListener('click', (event) => checkUser(event));

    let btnClear = document.querySelector('#btnClear');
    btnClear.addEventListener('click', () => clearLogin());
}

function LCpreencherONG(objONG) {
    document.querySelector('#imagemONG').src = objONG.logo;
    document.querySelector('#inputImagem').value = objONG.logo;
    document.querySelector('#inputNome').value = objONG.nome;
    document.querySelector('#inputCNPJ').value = objONG.cnpj;
    document.querySelector('#inputEndereco').value = objONG.endereco;
    document.querySelector('#inputTelefone').value = objONG.telefone;
    document.querySelector('#inputEmail').value = objONG.email;
} // end LCpreencherONG ( )

async function LCeditarONG(GV_ongid) {
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
    let GV_senhaatual = document.getElementById("inputSenhaAtual").value;
    let GV_novasenha = document.getElementById("inputSenhaNova").value;
    let GV_confirmarsenha = document.getElementById("inputConfirma").value;

    try{

        imagemcarregando.classList.remove("GV_esconder");

        let objsenha = {
            senha: GV_senhaatual
        }

        if(await VerificarRealizarLoginOngID(`${apiUrlloginong}/${GV_ongid}`, objsenha) == null){
            throw new Error('Senha incorreta!');
        }
        
        if(GV_novasenha !== GV_confirmarsenha){
            throw new Error('Senhas não coincidem!');
        }
        
        let objONG = {
            id: "0",
            nome: "",
            cnpj: "0",
            endereco: "",
            telefone: "",
            email: "",
            senha: "",
            logo: "",
            historia: "A ONG Aumigos foi fundada em 2015, com o objetivo de ajudar as animais abandonados e desaparecidos.",
            autenticacao: GV_senhaatual
        }

        objONG.logo = document.querySelector('#inputImagem').value;
        objONG.nome = document.querySelector('#inputNome').value;
        objONG.cnpj = document.querySelector('#inputCNPJ').value;
        objONG.endereco = document.querySelector('#inputEndereco').value;
        objONG.telefone = document.querySelector('#inputTelefone').value;
        objONG.email = document.querySelector('#inputEmail').value;
        objONG.senha = null;

        if(GV_novasenha !=""){
            objONG.senha = GV_novasenha;
        }

        await AC_updateONG(GV_ongid, objONG)
        location.href = location.href;
        imagemcarregando.classList.add("GV_esconder");
    }catch(error){

        imagemcarregando.classList.add("GV_esconder");

        if (error.message === 'Failed to fetch') {

            console.error('Erro de rede: Não foi possível acessar o servidor. Ele pode estar fora do ar ou o endereço pode estar incorreto.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if (error.message.includes('NetworkError') || error.message.includes('TypeError: Failed to fetch')) {

            console.error('Erro de rede: Falha na conexão. Verifique a rede ou o servidor.');
            LCdisplayMessage('ERRO: Servidor nao encontrado.');

        } else if(error.message == 'Senhas não coincidem!'){

            LCdisplayMessage('ERRO: Nova senha e confirmação não coincidem!');
            
        } else if(error.message == 'Senha incorreta!'){

            LCdisplayMessage('ERRO: Senha incorreta!');

        }

        console.log(error);

    }
} // end LCeditarONG ( )

function LCsairONG() {
    localStorage.clear();
    LCpaginaInicial();
} // end LCsairONG ( )

async function GV_obterDadosOngid(id) {
    //  definir dados locais
    let dados = null;
    //  tentar fazer a chamada
    // Definir a chamada HTTP do JSON Server
    await fetch(`${apiUrl5}/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response =>{
        if (response.ok) {
            dados = response.json();
        }
    });
    //  retornar
    return (dados);
} // end GV_obterDadosOngid ( )

async function startEditarONG() {
    let imagemcarregando = document.getElementById("GV_imagemdesitecarregando");
    let GV_dadosdaong = LCgetUser();

    try{
        document.querySelector('#btnUpdate').addEventListener('click', async event =>{await LCeditarONG(GV_dadosdaong.id); event.preventDefault();});
        document.querySelector('#btnSair').addEventListener('click', () => LCsairONG());
        // definir dados locais
        let objONG = await GV_obterDadosOngid(GV_dadosdaong.id);
        console.log(objONG);
        // testar se ONG foi encontrada
        if (!(Object.keys(objONG).length > 0)) {
            LCdisplayMessage("ERRO: Cadastro não encontrado.");
        }
        else {
            // preencher os dados da ong
            LCpreencherONG(objONG);
            // definir eventos
            //document.querySelector('#btnUpdate').addEventListener('click', async () => await LCeditarONG());
            //document.querySelector('#btnSair').addEventListener('click', () => LCsairONG());
        }
        imagemcarregando.classList.add("GV_esconder");
    }catch(error){
        imagemcarregando.classList.add("GV_esconder");
        console.log(error);
        LCdisplayMessage("ERRO: Servidor não encontrado!");
    }
} // end startEditarONG ( )

//---------------------------cadastro--------------------------

// Exibe mensagem em um elemento de ID msg
function HG_displayMessage(message) {
    document.getElementById('msg').innerHTML = '<div class="alert alert-warning">' + message + '</div>';
}

async function HG_createUser(user) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(apiUrlCad, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });
        console.log(user);
        HG_displayMessage("Sucesso ao criar usuário");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao criar usuário");
        throw new Error("Servidor nao encontrado!");
    }
}

//-----------------------------encontreiAnimal-----------------

a

async function HG_sendAnimal(imagembase64) {
    let dados_imagem = null;
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        await fetch(apiUrl7, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(imagembase64),
        }).then(async response => {
            if(response.ok){
                dados_imagem = response.json();
            }
        });
        console.log(imagembase64);
        LCdisplayMessage("Sucesso ao enviar pedido de reconhecimento");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao enviar pedido de reconhecimento");
        throw new Error("Serviço cognitivo não encontrado!");
    }
    return dados_imagem;
}

async function GV_enviaremail(obj_email) {
    //  tentar fazer a chamada
    try {
        //  definir a chamada HTTP do JSON Server
        const response = await fetch(apiUrl8, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(obj_email),
        });
        console.log(obj_email);
        LCdisplayMessage("Sucesso ao enviar email!");
    }
    //  chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao enviar email");
        throw new Error("Servidor nao encontrado!");
    }
}

//-----------------------------perfil--------------------------
async function HG_alterUser(user) {
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrlCad}/${user.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });
        // Mostrar resultado
        LCdisplayMessage("Sucesso ao editar usuário!");
    }
    // Chamada de erro
    catch (error) {
        console.error("Error:", error);
        throw new Error("Servidor não encontrado!");
    }
} // end HG_alterUser()

async function HG_deleteUser(id) {
    // Tentar fazer a chamada
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrlCad}/${id}`, {
            method: 'DELETE',
        });
        // Mostrar resultado
        LCdisplayMessage("Sucesso ao excluir usuário!");
    }
    // Chamada de erro
    catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao excluir usuário (JSON Server indisponível).");
        throw new Error("Servidor nao encontrado!");
    }
} // end HG_deleteUser()

async function HG_readUser(id) {
    let perfil = {};
    try {
        // Definir a chamada HTTP do JSON Server
        const response = await fetch(`${apiUrlCad}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        perfil = await response.json();
        // Mostrar resultado
        //console.log("Success:", perfil);
    } catch (error) {
        console.error("Error:", error);
        LCdisplayMessage("Erro ao ler perfil");
        throw new Error("Servidor nao encontrado!");
    }
    return perfil;
} // end HG_readUser()

//-----------------------------inicial ong---------------------
// Função para exibir mensagens na tela
function exibirMensagem(mensagem) {
    const msg = document.getElementById('msg');
    if (msg) {
        msg.innerHTML = `<div class="alert alert-warning">${mensagem}</div>`;
    }
}

// Função para ler animais do servidor JSON
async function lerAnimais() {
    let animais = [];
    try {
        const resposta = await fetch(apiUrlJP);
        animais = await resposta.json();
        console.log("Animais recebidos:", animais); // Verifica os dados recebidos
        return animais;
    } catch (erro) {
        console.error("Erro ao ler animais:", erro);
        return [];
    }
}

// Função para editar animais no servidor JSON
async function atualizarAnimal(id, animal) {
    try {
        await fetch(`${apiUrlJP}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(animal),
        });
        exibirMensagem("Sucesso ao editar animal!");
    } catch (erro) {
        console.error("Erro:", erro);
        exibirMensagem("Erro ao editar animal (servidor JSON indisponível).");
    }
}

// Função para carregar os animais na grade
async function carregarAnimais() {
    const gradeAnimais = document.getElementById('animal-grid');
    const animais = await lerAnimais();

    // Limpa a grade
    gradeAnimais.innerHTML = '';

    // Adiciona cartões de animais
    animais.forEach(animal => {
        const cartaoAnimal = document.createElement('div');
        cartaoAnimal.className = 'animal-card';
        cartaoAnimal.innerHTML = `
            <div class="GV_animaisongcrud">
                <div>
                    <img src="${animal.imagem}" alt="${animal.nome}" class="animal-img">
                </div>
                <p class="label-box">${animal.nome}</p>
            </div>
            <div class="crud-buttons">
                <button onclick="editarAnimal(${animal.id})">✏️</button>
                <button onclick="deletarAnimal(${animal.id})">🗑️</button>
            </div>
        `;
        gradeAnimais.appendChild(cartaoAnimal);
    });

    // Preenche espaços vazios com o botão '+' e adiciona o evento de redirecionamento
    while (gradeAnimais.children.length < 10) {
        const adicionarCartaoAnimal = document.createElement('div');
        adicionarCartaoAnimal.className = 'add-animal-card';
        adicionarCartaoAnimal.innerHTML = '+';
        adicionarCartaoAnimal.onclick = () => window.location.href = './cadastraranimal.html';
        gradeAnimais.appendChild(adicionarCartaoAnimal);
    }
}

// Função para mostrar o formulário de edição
function mostrarFormularioEdicao() {
    document.getElementById('animal-catalog').style.display = 'none';
    document.getElementById('edit-animal').style.display = 'block';
}

// Função para editar um animal
async function editarAnimal(id) {
    const animais = await lerAnimais();
    const animal = animais.find(animal => animal.id === id);
    if (animal) {
        document.getElementById('form-title').innerText = 'Editar Animal';
        document.getElementById('animal-id').value = animal.id;
        document.getElementById('sexo').value = animal.sexo;
        document.getElementById('idade').value = animal.idade;
        document.getElementById('raca').value = animal.raca;
        document.getElementById('cidade').value = animal.cidade;
        document.getElementById('historia').value = animal.historia;
        document.getElementById('tags').value = animal.tags.join(', ');

        mostrarFormularioEdicao();
    }
}

// Função para salvar um animal
async function salvarAnimal(event) {
    event.preventDefault();
    const id = document.getElementById('animal-id').value;
    const animal = {
        sexo: document.getElementById('sexo').value,
        idade: document.getElementById('idade').value,
        raca: document.getElementById('raca').value,
        cidade: document.getElementById('cidade').value,
        historia: document.getElementById('historia').value,
        tags: document.getElementById('tags').value.split(',').map(tag => tag.trim())
    };

    if (id) {
        await atualizarAnimal(id, animal);
    } else {
        await criarAnimal(animal);
    }

    cancelarEdicao();
    recarregarGradeAnimais();
}

// Função para criar um novo animal no servidor JSON
async function criarAnimal(animal) {
    try {
        await fetch(apiUrlJP, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(animal),
        });
        exibirMensagem("Sucesso ao criar animal!");
    } catch (erro) {
        console.error("Erro:", erro);
        exibirMensagem("Erro ao criar animal (servidor JSON indisponível).");
        throw new Error("Servidor não encontrado!");
    }
}

// Função para deletar um animal
async function deletarAnimal(id) {
    const confirmacao = true; //GV_alertexcluidoconfirm(`Tem certeza que deseja excluir o animal com ID: ${id}?`);
    if (confirmacao) {
        try {
            await fetch(`${apiUrlJP}/${id}`, {
                method: 'DELETE',
            });
            exibirMensagem("Sucesso ao excluir animal!");
            recarregarGradeAnimais();
        } catch (erro) {
            console.error("Erro:", erro);
            exibirMensagem("Erro ao excluir animal (servidor JSON indisponível).");
        }
    }
}

// Função para cancelar a edição
function cancelarEdicao() {
    document.getElementById('animal-catalog').style.display = 'block';
    document.getElementById('edit-animal').style.display = 'none';
    document.getElementById('edit-form').reset();
    document.getElementById('form-title').innerText = 'Cadastrar Animal';
}

// Função para recarregar a grade de animais
function recarregarGradeAnimais() {
    carregarAnimais();
}

// Adiciona o event listener para o formulário
document.getElementById('edit-form').addEventListener('submit', salvarAnimal);

// Verifica se o documento foi carregado antes de executar as funções
document.addEventListener('DOMContentLoaded', carregarAnimais);

//-----------------------------cadastro de animal - ong---------------------
/// Função para exibir mensagens na tela
function displayMessage(mensagem) {
    const msg = document.getElementById('msg');
    msg.innerHTML = `<div class="alert alert-warning">${mensagem}</div>`;
}

// Função para enviar os dados do formulário para o JSON Server
async function submitForm(event) {
    event.preventDefault();

    const form = document.getElementById('animalForm');
    const formData = new FormData(form);

    const tags = Array.from(document.querySelectorAll('input[name="tags"]:checked')).map(checkbox => checkbox.value);

    const animalData = {
        id: Date.now(), // ID único baseado no timestamp
        sexo: formData.get('sexo'),
        idade: formData.get('idade'),
        raca: formData.get('raca'),
        cidade: formData.get('cidade'),
        historia: formData.get('historia'),
        tags: tags
    };

    try {
        const response = await fetch('https://replit.com/@923928/JSONServer-Patinhas-Felizes#db.json', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(animalData),
        });
        if (response.ok) {
            displayMessage("Animal cadastrado com sucesso!");
        } else {
            displayMessage("Erro ao cadastrar animal.");
        }
    } catch (error) {
        console.error("Error:", error);
        displayMessage("Erro ao conectar com o servidor.");
    }

    console.log(animalData);
}

document.getElementById('animalForm').addEventListener('submit', submitForm);
