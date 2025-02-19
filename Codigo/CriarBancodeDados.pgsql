CREATE TABLE ong(
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cnpj VARCHAR(255) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  telefone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  logo VARCHAR(511) NOT NULL, 
  historia VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios(
id VARCHAR(255) NOT NULL PRIMARY KEY,
email VARCHAR(255) NOT NULL,
nome VARCHAR(255) NOT NULL,
senha VARCHAR(255) NOT NULL,
imagem VARCHAR(511),
etiquetas TEXT,
datanascimento DATE NOT NULL,
telefone VARCHAR(255) NOT NULL,
cpf VARCHAR(255) NOT NULL,
moradia VARCHAR(255) NOT NULL,
CONSTRAINT checarmoradia CHECK (moradia IN ('casa', 'apartamento'))
);

CREATE TABLE animais(
id VARCHAR(255) NOT NULL PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
especie VARCHAR(255) NOT NULL,
porte VARCHAR(1) NOT NULL,
donoid VARCHAR(255),
imagem TEXT NOT NULL,
vacinas TEXT,
etiquetas TEXT NOT NULL,
datasaida DATE,
dataentrada DATE NOT NULL,
descricao TEXT NOT NULL,
historia TEXT NOT NULL,
raca VARCHAR(50) NOT NULL,
datanascimento DATE NOT NULL,
castrado BOOLEAN NOT NULL,
sexo VARCHAR(1) NOT NULL,
CONSTRAINT fkdonoanimal FOREIGN KEY(donoid) REFERENCES usuarios(id),
CONSTRAINT checarporte CHECK (porte IN ('P', 'M', 'G')),
CONSTRAINT checarsexo CHECK (sexo IN ('F', 'M'))
);

CREATE TABLE comentarios(
id VARCHAR(255) NOT NULL PRIMARY KEY,
usuarioid VARCHAR(255) NOT NULL,
animalid VARCHAR(255) NOT NULL,
conteudo VARCHAR(1023) NOT NULL,
CONSTRAINT fkcomentariouser FOREIGN KEY(usuarioid) REFERENCES usuarios(id),
CONSTRAINT fkcomentarioanimal FOREIGN KEY(animalid) REFERENCES animais(id)
);

CREATE TABLE formularios(
id VARCHAR(255) NOT NULL PRIMARY KEY,
usuarioid VARCHAR(255) NOT NULL,
animalid VARCHAR(255) NOT NULL,
CONSTRAINT fkformularioanimal FOREIGN KEY(animalid) REFERENCES animais(id),
CONSTRAINT fkformulariouser FOREIGN KEY(usuarioid) REFERENCES usuarios(id)
);

INSERT INTO ong(
  id,
  nome,
  cnpj,
  endereco,
  telefone,
  email,
  senha,
  logo, 
  historia
) VALUES(
'0',
'ONG',
'20. 802. 329/0001-90',
'Av. Trinta e Um de Março, 1020 - Dom Cabral, Belo Horizonte - MG, 30535-000',
'+55 (31) 97141-2007',
'patinhasfelizesdiw@gmail.com',
'trabalhoDIW2024',
'https://quizizz.com/media/resource/gs/quizizz-media/quizzes/007aae49-a1f2-4d3b-b75b-ee004690adf3',
'A ONG Aumigos foi fundada em 2015, com o objetivo de ajudar as animais abandonados e desaparecidos.'
);

INSERT INTO animais(
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida) VALUES (
'1', 
'Amora', 
'https://www.petz.com.br/blog/wp-content/uploads/2024/04/como-saber-se-o-cachorro-e-de-raca-interna2.jpg',
'CA', 
'Vira-lata', 
'Gripe canina; Giárdia; Antirrábica; Leishmaniose', 
'2024-05-27', 
'2024-07-21', 
'F', 
'G', 
FALSE,
'Brincalhao; Gosta de caminhar; Extrovertido', 
'Linda, carinhosa e companheira! O que mais você precisa?', 
'Amora é uma cadela adorável, resgatada das ruas de Belo Horizonte por uma gentil alma que a levou para um abrigo de animais. Com seus olhos brilhantes e uma energia contagiante, Amora logo se tornou a favorita entre os voluntários do abrigo.  Apesar de ter sido resgatada, Amora não perdeu sua alegria de viver. Ela é uma cachorrinha agitada, sempre pronta para brincar e explorar novos lugares. Seu coração transborda amor e gratidão, e ela anseia por encontrar uma família para compartilhar todo esse amor.', 
NULL,
NULL
);

INSERT INTO animais(
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida) VALUES (
'2', 
'Otto', 
'https://images.pexels.com/photos/1135388/pexels-photo-1135388.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
'CA', 
'Dachshund Dog breed', 
'Gripe canina; Giárdia; Antirrábica; Leishmaniose', 
'2021-03-09', 
'2022-12-29', 
'M', 
'M', 
TRUE,
'Brincalhao; Gosta de caminhar; Extrovertido', 
'Brincalhão, divertido e alegre! Está pronto para a aventura?', 
'Otto é um cãozinho de três anos, com olhos castanhos que brilham de esperança. Ele foi encontrado vagando pelas ruas, magro e assustado, mas com um espírito resiliente. Desde então, Otto está em um abrigo aguardando por um lar definitivo. Quando Otto chegou ao abrigo, ele era tímido e reservado, claramente marcado pelas dificuldades que enfrentou na rua. Mas com o tempo, ele começou a mostrar sua verdadeira personalidade: um cão afetuoso e leal, que adora brincar e receber carinho. Otto é aquele tipo de CA que se aconchega no seu colo e parece agradecer por cada gesto de amor. Otto se dá bem com outros animais e é especialmente gentil com crianças. Ele adora passeios ao ar livre e tem um entusiasmo contagiante por explorar novos lugares. No entanto, o que ele mais deseja é um lar onde possa sentir-se seguro e amado.', 
NULL,
NULL
);

INSERT INTO animais(
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida) VALUES (
'3', 
'Bella', 
'https://petanjo.com/blog/wp-content/uploads/2021/11/labrador-tudo-sobre-a-raca.jpg',
'CA', 
'Labrador Retriever', 
'Gripe canina; Parvovírus; Antirrábica; Leptospirose', 
'2020-06-07', 
'2022-07-21', 
'F', 
'G', 
TRUE,
'Calmo; Extrovertido; Gosta de caminhar', 
'Carinhosa, calma e amorosa. Pronta para ser sua melhor amiga!', 
'Bella foi resgatada de um ambiente abusivo, onde vivia acorrentada. Agora, ela é uma cadela saudável e cheia de vida, procurando uma família que possa lhe dar todo o amor que merece. Bella é excelente com crianças e outros animais. Ela adora nadar e é apaixonada por bolinhas de tênis. Sempre disposta a aprender, Bella está pronta para qualquer desafio e treinamento.', 
NULL,
NULL
);

INSERT INTO animais(
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida) VALUES (
'4', 
'Luna', 
'https://www.petz.com.br/blog/wp-content/uploads/2022/10/gato-siames-muda-de-cor.jpg',
'GA', 
'Siamês', 
'Trivalente felina; Antirrábica', 
'2022-03-03', 
'2023-09-17', 
'F', 
'P', 
TRUE,
'Calmo; Introvertido; Caseiro', 
'Independente, elegante e cheia de personalidade.', 
'Luna foi abandonada quando era filhote e cresceu nas ruas. Apesar das dificuldades, ela nunca perdeu sua elegância. Agora, Luna está à procura de um lar que valorize sua independência e charme. Luna é uma gata que adora explorar e encontrar os melhores lugares para descansar ao sol. Ela é afetuosa quando quer, mas também gosta de seu espaço.', 
NULL,
NULL
);

INSERT INTO animais(
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) VALUES (
'5',
'Snowball',
'https://paixaoanimal.com/wp-content/uploads/2021/11/mini-coelho-anao-4.jpg.webp',
'CO',
'Coelho Anão',
'Mixomatose; Doença hemorrágica viral',
'2023-09-22',
'2024-03-20',
'M',
'P',
TRUE,
'Extrovertido; Brincalhao; Caseiro',
'Branquinho, fofinho e muito sociável.',
'Snowball foi encontrado abandonado em uma caixa de papelão, mas sua natureza afetuosa e brincalhona rapidamente conquistou todos no abrigo. Ele adora saltar e explorar novos ambientes. Snowball é perfeito para famílias que procuram um animal de estimação pequeno, mas cheio de energia e amor para dar.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'7',
'Max',
'https://th.bing.com/th/id/OIP.zlewrMx1krmznlPLItpWGwHaHa?rs=1&pid=ImgDetMain',
'CA',
'Pastor Alemão',
'Gripe canina; Parvovírus; Antirrábica; Leptospirose',
'2019-02-04',
'2023-05-11',
'M',
'G',
TRUE,
'Calmo; Extrovertido; Gosta de caminhar',
'Protetor, leal e cheio de energia.',
'Max era um cão de serviço aposentado que agora procura uma família para passar seus dias de forma tranquila. Ele é extremamente leal e sempre vigilante, tornando-o perfeito para quem procura um companheiro protetor. Max adora exercícios ao ar livre e está sempre pronto para uma nova aventura.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'8',
'Mittens',
'https://www.petz.com.br/blog/wp-content/uploads/2023/01/tipos-de-gato-persa-3.jpg',
'GA',
'Gato Persa',
'Trivalente felina; Antirrábica',
'2021-04-09',
'2023-01-28',
'F',
'M',
TRUE,
'Calmo; Introvertido; Caseiro',
'Curiosa, brincalhona e cheia de graça.',
'Mittens foi encontrada sozinha em uma casa abandonada. Ela tem uma personalidade brincalhona e adora explorar todos os cantos da casa. Mittens é muito afetuosa e sempre procura um colo para se aninhar. Ela é perfeita para quem procura uma companheira que adora brincar e se aconchegar.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'9',
'Rusty',
'https://s3.amazonaws.com/petz-cdm-stg/b1908605-967d-4b4c-8e44-acca7b50b8c7.jpeg',
'CA',
'Vira-lata',
'V8; Antirrábica',
'2021-03-16',
'2022-09-13',
'M',
'M',
TRUE,
'Brincalhao; Extrovertido; Gosta de caminhar',
'Amigável e adora brincadeiras.',
'Rusty foi encontrado sozinho e faminto em uma construção abandonada. Ele é um cão amigável e adora brincar com outros cães e crianças. Rusty está à procura de um lar onde possa se sentir seguro e amado.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'10',
'Abacaxi',
'https://s3.amazonaws.com/petz-cdm-stg/ff7720ed-a55e-4e24-b365-f12ba75119cc.jpeg',
'CA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2019-06-15',
'2022-09-06',
'M',
'P',
TRUE,
'Calmo; Introvertido; Caseiro',
'Calmo e adorável, sempre buscando um canto tranquilo.',
'Abacaxi viveu nas ruas por muitos anos antes de ser resgatado. Ele é calmo e adora um canto tranquilo para relaxar. Abacaxi precisa de um lar onde possa viver seus anos dourados com amor e conforto.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'11',
'Bunny',
'https://portalmelhoresamigos.com.br/wp-content/uploads/2016/12/arlequim.jpg',
'CO',
'Coelho Holandês',
'Mixomatose; VHD',
'2023-12-07',
'2024-04-05',
'F',
'P',
FALSE,
'Calmo; Introvertido; Caseiro',
'Pequeno e fofinho, sempre pronto para um carinho.',
'Bunny foi resgatada de um quintal onde vivia sem os devidos cuidados. Ela é doce e adora receber carinhos. Bunny procura uma família que possa lhe dar o amor e a atenção que ela merece.',
NULL,
NULL
);

INSERT INTO animais (
  id,
  nome,
  imagem,
  especie,
  raca,
  vacinas,
  datanascimento,
  dataentrada,
  sexo,
  porte,
  castrado,
  etiquetas,
  descricao,
  historia,
  donoid,
  datasaida
) 
VALUES (
  '12',
  'Nibbles',
  'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSp0-v2phIY4qAx0fO2kJc3hBpaYLTv9O6Wzw&s',
  'RO',
  'Hamster Sírio',
  'Não aplicável',
  '2024-04-03',
  '2024-09-20',
  'M',
  'P',
  FALSE,
  'Brincalhao; Extrovertido; Caseiro',
  'Cheio de personalidade, ágil e inteligente.',
  'Nibbles foi encontrado em uma gaiola pequena e suja. Agora ele está saudável e adora explorar novos brinquedos. Nibbles precisa de um lar que entenda suas necessidades e lhe ofereça um ambiente seguro e divertido.',
  NULL,
  NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'13',
'Oliver',
'https://s3.amazonaws.com/petz-cdm-stg/05d63905-908b-42c1-904c-31203b8f290b.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2022-02-02',
'2023-11-08',
'M',
'M',
TRUE,
'Extrovertido; Brincalhao; Gosta de caminhar',
'Aventureiro e sempre curioso.',
'Oliver foi resgatado de uma construção abandonada. Ele é curioso e adora explorar novos lugares. Oliver precisa de um lar que ofereça segurança e muito amor.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'14',
'Lola',
'https://s3.amazonaws.com/petz-cdm-stg/b3d9faae-4fd1-4690-9272-955931f00b81.jpeg',
'CA',
'Vira-lata',
'V10; Antirrábica',
'2020-09-25',
'2022-03-09',
'F',
'G',
TRUE,
'Calmo; Caseiro; Gosta de caminhar',
'Dócil e muito carinhosa.',
'Lola foi abandonada por seus antigos donos e encontrada em um estado lamentável. Com amor e cuidados, ela se recuperou e agora é uma cadela extremamente carinhosa que adora estar perto de pessoas. Lola precisa de uma família que a valorize e lhe ofereça todo o amor que ela merece.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'15',
'Coco',
'https://www.zooplus.pt/magazine/wp-content/uploads/2021/12/coelhos-angora_1.jpeg',
'CO',
'Coelho Angorá',
'Mixomatose; VHD',
'2022-07-30',
'2023-08-23',
'M',
'P',
FALSE,
'Brincalhao; Extrovertido; Caseiro',
'Brincalhão e muito esperto.',
'Coco foi encontrado em uma feira de animais em péssimas condições. Ele se recuperou bem e agora está pronto para um lar amoroso. Coco é brincalhão e gosta de explorar novos lugares. Precisa de um espaço seguro para brincar.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'16',
'Peanut',
'https://p2.trrsf.com/image/fget/cf/1200/900/middle/images.terra.com/2023/04/21/1723783590-porquinho-da-india-grama.jpg',
'RO',
'Porquinho-da-Índia',
'Não aplicável',
'2023-01-05',
'2024-04-18',
'F',
'P',
FALSE,
'Brincalhao; Extrovertido; Caseiro',
'Pequeno e cheio de energia.',
'Peanut foi abandonada em uma caixa de papelão na rua. Ela é uma porquinha-da-índia cheia de energia e curiosidade. Peanut precisa de um lar que entenda suas necessidades e possa oferecer um ambiente seguro e confortável.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'17',
'Nevoa',
'https://p2.trrsf.com/image/fget/cf/774/0/images.terra.com/2023/11/05/35067549-29190128798057.jpg',
'GA',
'Siamês',
'Trivalente felina; Antirrábica',
'2022-03-12',
'2022-05-01',
'F',
'P',
TRUE,
'Calmo; Introvertido; Caseiro',
'Independente e cheia de personalidade.',
'Nevoa foi abandonada quando era filhote e cresceu nas ruas. Apesar das dificuldades, ela nunca perdeu sua elegância. Agora, Nevoa está à procura de um lar que valorize sua independência e charme. Nevoa é uma gata que adora explorar e encontrar os melhores lugares para descansar ao sol. Ela é afetuosa quando quer, mas também gosta de seu espaço.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'18',
'Buddy',
'https://s3.amazonaws.com/petz-cdm-stg/38b6e13a-a136-41a6-9211-e1cb6410a2e7.jpeg',
'CA',
'Vira-lata',
'V8; Antirrábica',
'2021-08-02',
'2022-11-10',
'M',
'M',
TRUE,
'Brincalhao; Extrovertido; Gosta de caminhar',
'Amigável e cheio de energia.',
'Buddy foi resgatado de um abrigo superlotado. Ele é um cão amigável e cheio de energia, sempre pronto para brincar. Buddy precisa de uma família ativa que possa oferecer muitas aventuras e carinho.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'19',
'Mimi',
'https://s3.amazonaws.com/petz-cdm-stg/1ce10236-1abc-4393-92d8-8abf2b2bc7ef.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2020-06-03',
'2023-01-01',
'F',
'P',
TRUE,
'Calmo; Introvertido; Caseiro',
'Calma e adorável, sempre buscando um canto tranquilo.',
'Mimi viveu nas ruas por muitos anos antes de ser resgatada. Ela é calma e adora um canto tranquilo para relaxar. Mimi precisa de um lar onde possa viver seus anos dourados com amor e conforto.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'20',
'Twitch',
'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRP-gsWfe0txg82vewHpCS5O-KmTWldlSvPnw&s',
'RO',
'Hamster Roborovski',
'Não aplicável',
'2023-04-07',
'2023-08-09',
'M',
'P',
FALSE,
'Brincalhao; Extrovertido; Caseiro',
'Ágil e curioso, sempre explorando.',
'Twitch foi encontrado em um aquário abandonado. Ele é um hamster muito ativo e adora explorar seu ambiente. Twitch precisa de um lar que ofereça segurança e muitas atividades para mantê-lo entretido.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'21',
'Fluffy',
'https://farm7.staticflickr.com/6141/5968042213_9f4fa0a78d_b.jpg',
'CO',
'Coelho Mini Lop',
'Mixomatose; VHD',
'2022-04-30',
'2023-07-08',
'F',
'P',
FALSE,
'Calmo; Introvertido; Caseiro',
'Fofo e adorável, sempre pronto para receber carinho.',
'Fluffy foi resgatada de uma feira de animais em péssimas condições. Ela se recuperou bem e agora está pronta para um lar amoroso. Fluffy é brincalhona e gosta de explorar novos lugares. Precisa de um espaço seguro para brincar.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'22',
'Sandy',
'https://s3.amazonaws.com/petz-cdm-stg/2a3cf63c-251f-45d7-beec-8769e3c33e3d.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2021-05-19',
'2022-12-21',
'F',
'M',
TRUE,
'Calmo; Introvertido; Caseiro',
'Afetuosa e adora estar perto de pessoas.',
'Sandy foi resgatada de uma situação de maus-tratos. Ela é extremamente afetuosa e adora estar perto de pessoas. Sandy precisa de um lar que lhe ofereça segurança e muito amor.',
NULL,
NULL
);

INSERT INTO animais (
  id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'26',
'Shadow',
'https://s3.amazonaws.com/petz-cdm-stg/905eb3ac-95a9-43fd-ac0a-4242410f5f0d.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2020-03-30',
'2022-09-29',
'M',
'M',
TRUE,
'Calmo; Introvertido; Caseiro',
'Misterioso e independente.',
'Shadow foi encontrado vagando sozinho em uma área industrial. Ele é um gato independente que prefere observar de longe, mas se apega profundamente às pessoas em quem confia. Shadow precisa de um lar calmo e pacífico.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'27',
'Sasha',
'https://s3.amazonaws.com/petz-cdm-stg/f3537002-9c00-4cc2-ae9e-f45cb22e0d7e.jpeg',
'CA',
'Vira-lata',
'V10; Antirrábica',
'2021-07-11',
'2022-10-10',
'F',
'G',
TRUE,
'Calmo; Caseiro; Gosta de caminhar',
'Dócil e muito amorosa, sempre pronta para um abraço.',
'Sasha foi resgatada de uma situação de negligência. Ela é extremamente carinhosa e adora estar perto das pessoas, sempre pronta para um abraço. Sasha precisa de uma família que possa lhe oferecer o amor e cuidado que ela merece.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'28',
'Nina',
'https://www.zooplus.pt/magazine/wp-content/uploads/2020/05/castor-rex-kaninchen-768x528-1.jpeg',
'CO',
'Coelho Rex',
'Mixomatose; VHD',
'2023-06-03',
'2024-01-28',
'F',
'M',
FALSE,
'Brincalhao; Extrovertido; Caseiro',
'Pequena e adorável, sempre pronta para brincar.',
'Nina foi resgatada de uma situação de abandono. Ela é brincalhona e adora explorar, sempre pronta para uma nova aventura. Nina precisa de um lar que possa oferecer segurança e muito carinho.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'29',
'Scamp',
'https://i.natgeofe.com/n/1e471983-8c9f-4d38-99f7-7c988cdc93ea/Minden_00413092_square.jpg',
'RO',
'Chinchila',
'Não aplicável',
'2024-02-21',
'2024-08-10',
'M',
'P',
FALSE,
'Brincalhao; Extrovertido; Caseiro',
'Ágil e cheio de personalidade.',
'Scamp foi resgatado de uma casa onde era mantido em más condições. Ele é ágil e adora pular e explorar. Scamp precisa de um lar que ofereça segurança e muitos brinquedos para mantê-lo ativo.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'30',
'Simba',
'https://s3.amazonaws.com/petz-cdm-stg/cf81a1e8-8d9f-4db8-b6db-224ed881cd1f.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2022-03-07',
'2022-12-09',
'M',
'M',
TRUE,
'Extrovertido; Brincalhao; Gosta de caminhar',
'Aventureiro e cheio de energia.',
'Simba foi encontrado em um terreno baldio, magro e cheio de pulgas. Com cuidados, ele se recuperou e agora está pronto para um lar amoroso. Simba é curioso e adora explorar, sempre cheio de energia para novas aventuras.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'32',
'Biscuit',
'https://www.petz.com.br/blog/wp-content/uploads/2022/04/coelho-leao-interna-1.jpg',
'CO',
'Coelho Lionhead',
'Mixomatose; VHD',
'2023-05-01',
'2023-09-28',
'M',
'P',
FALSE,
'Calmo; Introvertido; Caseiro',
'Pequeno e cheio de amor para dar.',
'Biscuit foi encontrado em uma feira de animais em péssimas condições. Ele se recuperou bem e agora está pronto para um lar amoroso. Biscuit é brincalhão e gosta de explorar novos lugares. Precisa de um espaço seguro para brincar.',
NULL,
NULL
);

INSERT INTO animais (
id,
nome,
imagem,
especie,
raca,
vacinas,
datanascimento,
dataentrada,
sexo,
porte,
castrado,
etiquetas,
descricao,
historia,
donoid,
datasaida
) 
VALUES (
'33',
'Charlie',
'https://s3.amazonaws.com/petz-cdm-stg/4b7d809d-8c80-4ff7-b7e3-03d1ad2bdb41.jpeg',
'GA',
'Vira-lata',
'Trivalente felina; Antirrábica',
'2022-08-06',
'2023-01-20',
'M',
'M',
TRUE,
'Extrovertido; Brincalhao; Gosta de caminhar',
'Amigável e cheio de vida.',
'Charlie foi resgatado de uma situação de maus-tratos. Ele é amigável e cheio de vida, sempre pronto para brincar. Charlie precisa de um lar que possa oferecer amor e segurança.',
NULL,
NULL
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'99',
'jose@gmail.com',
'Jose',
'123',
'https://static.vecteezy.com/system/resources/previews/008/422/689/original/social-media-avatar-profile-icon-isolated-on-square-background-vector.jpg',
'Calmo; Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'32',
'lucascarneiromalta@gmail.com',
'Lucas Carneiro',
'12345',
'https://pps.whatsapp.net/v/t61.24694-24/338765211_164181686528529_8450649705517588119_n.jpg?ccb=11-4&oh=01_Q5AaIIrvoIMG9KBeASudPP0nox53i2FyozDz_cikXtAT1w4F&oe=668ECDF7&_nc_sid=e6ed6c&_nc_cat=109',
'Calmo; Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'21',
'leticia1234@gmail.com',
'Leticia Silva',
'12345',
'https://static.vecteezy.com/system/resources/previews/008/422/689/original/social-media-avatar-profile-icon-isolated-on-square-background-vector.jpg',
'Calmo; Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'37',
'anajultorres@gmail.com',
'Ana Júlia Torres',
'vermelho',
'https://img.freepik.com/fotos-gratis/rato-branco-fofo-em-pe-no-quarto-rosa_23-2150760586.jpg',
'Brincalhao; Gosta de caminhar; Extrovertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'94',
'cintiamtorres@gmail.com',
'Cíntia Torres',
'12345678',
'https://images.pexels.com/photos/733872/pexels-photo-733872.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',
'Gosta de caminhar; Extrovertido; Calmo',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'35',
'jptorres2009@gmail.com',
'João Pedro Torres',
'123',
'https://pps.whatsapp.net/v/t61.24694-24/338765211_164181686528529_8450649705517588119_n.jpg?ccb=11-4&oh=01_Q5AaIICIvbYJfx5MTm-UBf5LZ-x9lOuVIy9gRPnmlVbo5Ezb&oe=668FE737&_nc_sid=e6ed6c&_nc_cat=109',
'Calmo; Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'90',
'teste@gmail.com',
'Valedo',
'123456',
'https://static.vecteezy.com/system/resources/previews/008/422/689/original/social-media-avatar-profile-icon-isolated-on-square-background-vector.jpg',
'Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO usuarios(
id,
email,
nome,
senha,
imagem,
etiquetas,
datanascimento,
telefone,
cpf,
moradia
) VALUES (
'1729096477032',
'teste2@gmail.com',
'Rafael',
'123456',
'https://static.vecteezy.com/system/resources/previews/008/422/689/original/social-media-avatar-profile-icon-isolated-on-square-background-vector.jpg',
'Brincalhao; Caseiro; Introvertido',
'1997-07-10',
'(31) 98888-7777',
'444.555.666-99',
'casa'
);

INSERT INTO formularios(
id,
usuarioid,
animalid
) VALUES (
'1729102662806',
'1729096477032',
'4'
);

INSERT INTO comentarios(
id,
usuarioid,
animalid,
conteudo
) VALUES (
'483',
'32',
'1',
'Que linda, gente! Adorei!'
);

INSERT INTO comentarios(
id,
usuarioid,
animalid,
conteudo
) VALUES (
'615',
'32',
'5',
'Fofoooo!'
);

INSERT INTO comentarios(
id,
usuarioid,
animalid,
conteudo
) VALUES (
'271',
'35',
'2',
'Muito bonito!!!'
);


