package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.AutorResponseDto;
import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.LivroRepository;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class LivroCrudPadraoService implements LivroCrudService{
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LivroRepository livroRepository;

    @Override
    public LivroResponseDto adicionarLivro(LivroPostPutDto livroPostPutDto){
        Livro livro = modelMapper.map(livroPostPutDto, Livro.class);
        return modelMapper.map(livroRepository.save(livro), LivroResponseDto.class);
    }

    @Override
    public List<LivroResponseDto> buscarTodosLivros(){
        return livroRepository.findAll()
                .stream()
                .map(livro -> modelMapper.map(livro, LivroResponseDto.class))
                .toList();
    }

    @Override
    public LivroResponseDto buscarLivro(Long isbn) {
        return modelMapper.map(buscarLivrosExistente(isbn), LivroResponseDto.class);
    }

    @Override
    public LivroResponseDto buscarLivro(String titulo) {
        return modelMapper.map(buscarLivrosTitulo(titulo), LivroResponseDto.class);
    }

    @Override
    public LivroResponseDto atualizarLivro(Long isbn, LivroPostPutDto livroPostPutDto) {
        Livro livro = buscarLivrosExistente(isbn);
        livro.setAno(livroPostPutDto.getAno());
        livro.setEdicao(livroPostPutDto.getEdicao());
        livro.setTituloLivro(livroPostPutDto.getTitulo());
        return modelMapper.map(livroRepository.save(livro), LivroResponseDto.class);
    }

    @Override
    public void removerLivro(Long isbn) {
        livroRepository.delete(buscarLivrosExistente(isbn));
    }

    private Livro buscarLivrosExistente(Long isbn){
        return livroRepository.findById(isbn).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    private Livro buscarLivrosTitulo(String titulo){
        Livro retorno = null;
        List<Livro> livros = livroRepository.findAll();
        for(Livro l : livros){
            if(l.getTituloLivro().equals(titulo.toLowerCase())){
                retorno = l;
            }
        }

        return buscarLivrosExistente(retorno.getIsbn());
        
    }

}
