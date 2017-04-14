package com.aurea.deadcode.service;

import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.aurea.deadcode.jpa.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;

public class RepoOrchestratorServiceImplTest {
    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private GitService gitService;
    
    @Mock
    private UnderstandService undService;
    
    @Mock
    private CodeAnalyzerService caService;
    
    @Mock
    private ScmRepoRepository scmRepoRepository;
    
    @InjectMocks
    private RepoOrchestratorServiceImpl orchestrator;
    
    @Test
    public void shouldOrchestrateServicesOnNewRepoAdded() throws Exception {
        final String uuid = UUID.randomUUID().toString();
        final ScmRepo repo = mock(ScmRepo.class);
        when(scmRepoRepository.findByUuid(uuid)).thenReturn(repo);
        orchestrator.newRepoAdded(uuid);
        verify(scmRepoRepository).findByUuid(uuid);
        verify(gitService).cloneNewRepo(repo);
        verify(caService).analyzeRepo(repo);
        verify(repo).setAnalysisStarted(any(Date.class));
        verify(repo).setAnalysisEnded(any(Date.class));
        verify(scmRepoRepository).save(repo);
    }

}
